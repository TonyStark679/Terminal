package com.example.terminal.engine

import android.util.Log
import com.example.theme.model.TerminalTheme
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.*

class TerminalSession(
    val id: String,
    title: String,
    initialWorkingDir: File,
    private val shellEnvironment: ShellEnvironment,
    initialTheme: TerminalTheme,
    private val maxScrollback: Int = 2000,
    private val onCommandFinished: ((command: String, exitCode: Int, workingDir: String, sessionId: String) -> Unit)? = null,
    private val onAiCommandTriggered: ((command: String, session: TerminalSession) -> Unit)? = null,
    private val onEditCommandTriggered: ((filePath: String) -> Unit)? = null
) {
    private val tag = "TerminalSession-$id"
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _title = MutableStateFlow(title)
    val titleFlow: StateFlow<String> = _title.asStateFlow()

    private val _workingDirectory = MutableStateFlow(initialWorkingDir.absolutePath)
    val workingDirectoryFlow: StateFlow<String> = _workingDirectory.asStateFlow()

    private val _lines = MutableStateFlow<List<TerminalLine>>(emptyList())
    val lines: StateFlow<List<TerminalLine>> = _lines.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning.asStateFlow()

    var lastCommand: String = ""
        private set
    var lastExitCode: Int = 0
        private set
    var recentStderr: String = ""
        private set
    var recentStdout: String = ""
        private set

    private val ansiParser = AnsiParser(initialTheme)

    private var process: Process? = null
    private var stdinWriter: OutputStream? = null

    fun updateTheme(theme: TerminalTheme) {
        ansiParser.updateTheme(theme)
    }

    fun setTitle(newTitle: String) {
        _title.value = newTitle
    }

    fun start() {
        if (_isRunning.value) return
        scope.launch {
            try {
                val workingDir = File(_workingDirectory.value).let {
                    if (it.exists() && it.isDirectory) it else shellEnvironment.homeDir
                }

                val pb = ProcessBuilder("/system/bin/sh")
                pb.directory(workingDir)
                val env = pb.environment()
                env.clear()
                env.putAll(shellEnvironment.getEnvironmentMap())

                val proc = pb.start()
                process = proc
                stdinWriter = proc.outputStream
                _isRunning.value = true

                // Welcome message
                appendLine(
                    TerminalLine.simple(
                        text = "ROHAN AI TERMINAL [Session: ${_title.value}]",
                        color = ansiParser.parse("\u001B[1;36m").firstOrNull()?.spans?.firstOrNull()?.foreground,
                        isBold = true
                    )
                )
                appendLine(
                    TerminalLine.simple(
                        text = "Working Directory: ${workingDir.absolutePath} | Linux Bionic Shell",
                        isDim = false
                    )
                )
                appendLine(
                    TerminalLine.simple(
                        text = "Type 'help' for commands, 'ai help' for AI assistant, 'tot' for Gemma ToT.",
                        isDim = true
                    )
                )

                // Read stdout & stderr
                launch { readStream(proc.inputStream, isError = false) }
                launch { readStream(proc.errorStream, isError = true) }

                // Wait for process completion
                val exitCode = proc.waitFor()
                lastExitCode = exitCode
                _isRunning.value = false
                appendLine(
                    TerminalLine.simple(
                        text = "[Process exited with code $exitCode]",
                        isError = exitCode != 0
                    )
                )
            } catch (e: Exception) {
                Log.e(tag, "Failed to start shell process", e)
                _isRunning.value = false
                appendLine(
                    TerminalLine.simple(
                        text = "Error starting shell: ${e.message}",
                        isError = true
                    )
                )
            }
        }
    }

    private suspend fun readStream(inputStream: InputStream, isError: Boolean) = withContext(Dispatchers.IO) {
        val reader = BufferedReader(InputStreamReader(inputStream, Charsets.UTF_8))
        val buffer = CharArray(1024)
        val sb = StringBuilder()

        try {
            var charsRead: Int
            while (reader.read(buffer).also { charsRead = it } != -1) {
                val chunk = String(buffer, 0, charsRead)
                if (isError) {
                    recentStderr = (recentStderr + chunk).takeLast(2000)
                } else {
                    recentStdout = (recentStdout + chunk).takeLast(4000)
                }

                // Check for clear screen escape code: \033[2J
                if (chunk.contains("\u001B[2J") || chunk.contains("\u001Bc")) {
                    clear()
                }

                // Update current working directory if cd output or prompt
                detectWorkingDirectory(chunk)

                val parsedLines = ansiParser.parse(chunk, isError = isError)
                appendLines(parsedLines)
            }
        } catch (e: IOException) {
            // Process terminated or stream closed
        }
    }

    private fun detectWorkingDirectory(chunk: String) {
        // Look for prompt or PWD echo
        val pwdPattern = Regex("""ROHAN_PWD=(/[^\r\n]+)""")
        val match = pwdPattern.find(chunk)
        if (match != null) {
            val dir = match.groupValues[1]
            if (File(dir).exists()) {
                _workingDirectory.value = dir
            }
        }
    }

    fun executeCommand(rawCommand: String) {
        val cmd = rawCommand.trim()
        if (cmd.isEmpty()) {
            write("\n")
            return
        }

        lastCommand = cmd

        // Check for Editor command: edit <filename>
        if (cmd.startsWith("edit ") || cmd == "edit") {
            val fileArg = cmd.removePrefix("edit").trim()
            val targetFile = if (fileArg.isNotEmpty()) {
                if (fileArg.startsWith("/")) File(fileArg) else File(_workingDirectory.value, fileArg)
            } else {
                File(_workingDirectory.value, "untitled.txt")
            }
            onEditCommandTriggered?.invoke(targetFile.absolutePath)
            appendLine(TerminalLine.simple("Opening in built-in Code Editor: ${targetFile.name}"))
            return
        }

        // Check for AI command interception: ai ... or /ai ... or qwen ... or tot ...
        if (cmd.startsWith("ai ") || cmd.startsWith("/ai ") || cmd == "ai" || cmd == "/ai" ||
            cmd.startsWith("qwen ") || cmd == "qwen" ||
            cmd.startsWith("tot ") || cmd.startsWith("gemma-tot ") || cmd == "tot"
        ) {
            onAiCommandTriggered?.invoke(cmd, this)
        }

        // Custom built-in clear
        if (cmd == "clear" || cmd == "cls") {
            clear()
            return
        }

        // Send command to shell process
        scope.launch(Dispatchers.IO) {
            try {
                // Prepend command echo in terminal
                appendLine(
                    TerminalLine.simple(
                        text = "$ $cmd",
                        color = ansiParser.parse("\u001B[1;32m").firstOrNull()?.spans?.firstOrNull()?.foreground,
                        isBold = true
                    )
                )

                stdinWriter?.let { out ->
                    // Append PWD echo probe silently after command so we track cd changes accurately
                    val payload = "$cmd; echo \"ROHAN_PWD=\$(pwd)\"\n"
                    out.write(payload.toByteArray(Charsets.UTF_8))
                    out.flush()
                }

                onCommandFinished?.invoke(cmd, 0, _workingDirectory.value, id)
            } catch (e: Exception) {
                Log.e(tag, "Failed to write command to stdin", e)
                appendLine(TerminalLine.simple("Write error: ${e.message}", isError = true))
            }
        }
    }

    fun write(text: String) {
        scope.launch(Dispatchers.IO) {
            try {
                stdinWriter?.let { out ->
                    out.write(text.toByteArray(Charsets.UTF_8))
                    out.flush()
                }
            } catch (e: Exception) {
                Log.e(tag, "Write error", e)
            }
        }
    }

    fun sendCtrlC() {
        scope.launch(Dispatchers.IO) {
            try {
                stdinWriter?.let { out ->
                    out.write(3) // ETX
                    out.flush()
                }
                appendLine(TerminalLine.simple("^C", isBold = true))
            } catch (e: Exception) {
                Log.e(tag, "Ctrl+C error", e)
            }
        }
    }

    fun sendCtrlD() {
        scope.launch(Dispatchers.IO) {
            try {
                stdinWriter?.let { out ->
                    out.write(4) // EOT
                    out.flush()
                }
            } catch (e: Exception) {
                Log.e(tag, "Ctrl+D error", e)
            }
        }
    }

    fun sendKey(key: String) {
        when (key.uppercase()) {
            "ESC" -> write("\u001B")
            "TAB" -> write("\t")
            "HOME" -> write("\u001B[H")
            "END" -> write("\u001B[F")
            "UP", "↑" -> write("\u001B[A")
            "DOWN", "↓" -> write("\u001B[B")
            "RIGHT", "→" -> write("\u001B[C")
            "LEFT", "←" -> write("\u001B[D")
            "ENTER" -> write("\n")
            "BACKSPACE" -> write("\u007F")
            else -> write(key)
        }
    }

    fun clear() {
        _lines.value = emptyList()
    }

    fun appendLine(line: TerminalLine) {
        val current = _lines.value.toMutableList()
        current.add(line)
        if (current.size > maxScrollback) {
            _lines.value = current.takeLast(maxScrollback)
        } else {
            _lines.value = current
        }
    }

    private fun appendLines(newLines: List<TerminalLine>) {
        if (newLines.isEmpty()) return
        val current = _lines.value.toMutableList()
        current.addAll(newLines)
        if (current.size > maxScrollback) {
            _lines.value = current.takeLast(maxScrollback)
        } else {
            _lines.value = current
        }
    }

    fun destroy() {
        scope.cancel()
        try {
            stdinWriter?.close()
        } catch (_: Exception) {}
        try {
            process?.destroy()
        } catch (_: Exception) {}
        _isRunning.value = false
    }
}
