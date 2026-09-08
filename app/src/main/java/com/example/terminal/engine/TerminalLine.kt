package com.example.terminal.engine

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

data class TerminalSpan(
    val text: String,
    val foreground: Color? = null,
    val background: Color? = null,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val isDim: Boolean = false,
    val isReverse: Boolean = false
) {
    val fontWeight: FontWeight get() = if (isBold) FontWeight.Bold else FontWeight.Normal
    val fontStyle: FontStyle get() = if (isItalic) FontStyle.Italic else FontStyle.Normal
    val textDecoration: TextDecoration get() = if (isUnderline) TextDecoration.Underline else TextDecoration.None
}

data class TerminalLine(
    val spans: List<TerminalSpan>,
    val timestamp: Long = System.currentTimeMillis(),
    val isError: Boolean = false,
    val isCommandEcho: Boolean = false
) {
    val rawText: String by lazy {
        spans.joinToString("") { it.text }
    }

    companion object {
        fun simple(
            text: String,
            color: Color? = null,
            isError: Boolean = false,
            isBold: Boolean = false,
            isDim: Boolean = false
        ): TerminalLine {
            return TerminalLine(
                spans = listOf(TerminalSpan(text = text, foreground = color, isBold = isBold, isDim = isDim)),
                isError = isError
            )
        }
    }
}
