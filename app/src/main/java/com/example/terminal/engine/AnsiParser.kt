package com.example.terminal.engine

import androidx.compose.ui.graphics.Color
import com.example.theme.model.TerminalTheme

class AnsiParser(private var theme: TerminalTheme) {

    fun updateTheme(newTheme: TerminalTheme) {
        theme = newTheme
    }

    private var currentFg: Color? = null
    private var currentBg: Color? = null
    private var isBold = false
    private var isItalic = false
    private var isUnderline = false
    private var isDim = false
    private var isReverse = false

    fun resetState() {
        currentFg = null
        currentBg = null
        isBold = false
        isItalic = false
        isUnderline = false
        isDim = false
        isReverse = false
    }

    /**
     * Parses a block of text into a list of styled TerminalLines.
     */
    fun parse(rawText: String, isError: Boolean = false): List<TerminalLine> {
        val lines = mutableListOf<TerminalLine>()
        val rawLines = rawText.split("\n")

        for (i in rawLines.indices) {
            val rawLine = rawLines[i]
            val spans = parseLineToSpans(rawLine)
            if (spans.isNotEmpty() || i < rawLines.size - 1) {
                lines.add(TerminalLine(spans = spans, isError = isError))
            }
        }
        return lines
    }

    private fun parseLineToSpans(line: String): List<TerminalSpan> {
        val spans = mutableListOf<TerminalSpan>()
        val sb = StringBuilder()
        var idx = 0
        val len = line.length

        while (idx < len) {
            val c = line[idx]

            // Handle ESC sequence: \u001B[ ...
            if (c == '\u001B' && idx + 1 < len) {
                val nextChar = line[idx + 1]

                // CSI sequence: ESC [
                if (nextChar == '[') {
                    if (sb.isNotEmpty()) {
                        spans.add(createSpan(sb.toString()))
                        sb.clear()
                    }

                    idx += 2
                    val seqBuilder = StringBuilder()
                    while (idx < len && !isCsiTerminator(line[idx])) {
                        seqBuilder.append(line[idx])
                        idx++
                    }

                    if (idx < len) {
                        val commandChar = line[idx]
                        idx++
                        applyCsiCommand(commandChar, seqBuilder.toString())
                    }
                    continue
                }
                // OSC sequence: ESC ] ... (BEL \u0007 or ESC \)
                else if (nextChar == ']') {
                    if (sb.isNotEmpty()) {
                        spans.add(createSpan(sb.toString()))
                        sb.clear()
                    }
                    idx += 2
                    while (idx < len && line[idx] != '\u0007' && !(line[idx] == '\u001B' && idx + 1 < len && line[idx + 1] == '\\')) {
                        idx++
                    }
                    if (idx < len && line[idx] == '\u0007') idx++
                    else if (idx + 1 < len && line[idx] == '\u001B' && line[idx + 1] == '\\') idx += 2
                    continue
                }
            }

            // Carriage return: reset line text in progress (common in progress bars)
            if (c == '\r') {
                if (sb.isNotEmpty()) {
                    sb.clear()
                    spans.clear()
                }
                idx++
                continue
            }

            // Tab stop: expand to 4 spaces
            if (c == '\t') {
                sb.append("    ")
                idx++
                continue
            }

            // Backspace
            if (c == '\b' || c == '\u007F') {
                if (sb.isNotEmpty()) {
                    sb.deleteCharAt(sb.length - 1)
                }
                idx++
                continue
            }

            sb.append(c)
            idx++
        }

        if (sb.isNotEmpty()) {
            spans.add(createSpan(sb.toString()))
        }

        return spans
    }

    private fun isCsiTerminator(c: Char): Boolean {
        return c in '@'..'~'
    }

    private fun applyCsiCommand(cmd: Char, argsStr: String) {
        when (cmd) {
            'm' -> {
                // SGR (Select Graphic Rendition)
                if (argsStr.isEmpty() || argsStr == "0") {
                    resetState()
                    return
                }
                val tokens = argsStr.split(';').mapNotNull { it.toIntOrNull() }
                var i = 0
                while (i < tokens.size) {
                    when (val code = tokens[i]) {
                        0 -> resetState()
                        1 -> isBold = true
                        2 -> isDim = true
                        3 -> isItalic = true
                        4 -> isUnderline = true
                        7 -> isReverse = true
                        22 -> { isBold = false; isDim = false }
                        23 -> isItalic = false
                        24 -> isUnderline = false
                        27 -> isReverse = false
                        in 30..37 -> currentFg = getStandardAnsiColor(code - 30, bright = false)
                        39 -> currentFg = null // default
                        in 40..47 -> currentBg = getStandardAnsiColor(code - 40, bright = false)
                        49 -> currentBg = null // default
                        in 90..97 -> currentFg = getStandardAnsiColor(code - 90, bright = true)
                        in 100..107 -> currentBg = getStandardAnsiColor(code - 100, bright = true)
                        38 -> {
                            // Extended foreground: 38;5;n (256 colors) or 38;2;r;g;b (24-bit)
                            if (i + 2 < tokens.size && tokens[i + 1] == 5) {
                                currentFg = get256Color(tokens[i + 2])
                                i += 2
                            } else if (i + 4 < tokens.size && tokens[i + 1] == 2) {
                                currentFg = Color(tokens[i + 2], tokens[i + 3], tokens[i + 4])
                                i += 4
                            }
                        }
                        48 -> {
                            // Extended background
                            if (i + 2 < tokens.size && tokens[i + 1] == 5) {
                                currentBg = get256Color(tokens[i + 2])
                                i += 2
                            } else if (i + 4 < tokens.size && tokens[i + 1] == 2) {
                                currentBg = Color(tokens[i + 2], tokens[i + 3], tokens[i + 4])
                                i += 4
                            }
                        }
                    }
                    i++
                }
            }
            'J' -> {
                // Erase in Display
                // Handled at session level when \033[2J received
            }
        }
    }

    private fun getStandardAnsiColor(index: Int, bright: Boolean): Color {
        val ansi = theme.ansi
        return if (!bright) {
            when (index) {
                0 -> ansi.black
                1 -> ansi.red
                2 -> ansi.green
                3 -> ansi.yellow
                4 -> ansi.blue
                5 -> ansi.magenta
                6 -> ansi.cyan
                7 -> ansi.white
                else -> theme.foreground
            }
        } else {
            when (index) {
                0 -> ansi.brightBlack
                1 -> ansi.brightRed
                2 -> ansi.brightGreen
                3 -> ansi.brightYellow
                4 -> ansi.brightBlue
                5 -> ansi.brightMagenta
                6 -> ansi.brightCyan
                7 -> ansi.brightWhite
                else -> theme.foreground
            }
        }
    }

    private fun get256Color(colorCode: Int): Color {
        if (colorCode in 0..7) return getStandardAnsiColor(colorCode, false)
        if (colorCode in 8..15) return getStandardAnsiColor(colorCode - 8, true)

        // 6x6x6 color cube: 16 + 36*r + 6*g + b
        if (colorCode in 16..231) {
            val idx = colorCode - 16
            val r = (idx / 36) * 51
            val g = ((idx % 36) / 6) * 51
            val b = (idx % 6) * 51
            return Color(r, g, b)
        }

        // Grayscale 232..255
        if (colorCode in 232..255) {
            val gray = 8 + (colorCode - 232) * 10
            return Color(gray, gray, gray)
        }

        return theme.foreground
    }

    private fun createSpan(text: String): TerminalSpan {
        var fg = currentFg
        var bg = currentBg
        if (isReverse) {
            val tmp = fg ?: theme.foreground
            fg = bg ?: theme.background
            bg = tmp
        }
        return TerminalSpan(
            text = text,
            foreground = fg,
            background = bg,
            isBold = isBold,
            isItalic = isItalic,
            isUnderline = isUnderline,
            isDim = isDim,
            isReverse = isReverse
        )
    }
}
