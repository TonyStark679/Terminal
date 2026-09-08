package com.example.ai.model

import com.example.terminal.engine.TerminalSession
import java.io.File

data class AiContextPayload(
    val level: AiContextLevel,
    val targetModel: String,
    val workingDirectory: String,
    val lastCommand: String?,
    val exitCode: Int?,
    val recentStderr: String?,
    val recentOutputLines: List<String>,
    val directoryFiles: List<String>,
    val estimatedTokens: Int,
    val formattedContextText: String
)

object AiContextBuilder {

    fun buildContext(
        session: TerminalSession?,
        config: AiModelConfig,
        customPrompt: String,
        targetModelName: String
    ): AiContextPayload {
        val lastCmd = session?.lastCommand?.takeIf { it.isNotBlank() }
        val exitCode = session?.lastExitCode
        val stderr = session?.recentStderr?.takeIf { it.isNotBlank() }
        val cwd = session?.workingDirectoryFlow?.value ?: "/data/user/0/com.example/files/home"

        val dirFiles = if (config.contextLevel in listOf(AiContextLevel.DIRECTORY, AiContextLevel.PROJECT)) {
            val dir = File(cwd)
            if (dir.exists() && dir.isDirectory) {
                dir.listFiles()?.take(20)?.map { "${if (it.isDirectory) "[DIR] " else "[FILE] "} ${it.name}" } ?: emptyList()
            } else emptyList()
        } else emptyList()

        val recentLines = if (config.contextLevel in listOf(AiContextLevel.TERMINAL, AiContextLevel.DIRECTORY, AiContextLevel.PROJECT)) {
            session?.lines?.value?.takeLast(30)?.map { it.rawText } ?: emptyList()
        } else emptyList()

        val sb = StringBuilder()
        sb.appendLine("=== ROHAN TERMINAL CONTEXT ===")
        sb.appendLine("Working Directory: $cwd")
        if (config.contextLevel != AiContextLevel.NONE) {
            lastCmd?.let { sb.appendLine("Last Command: $it") }
            exitCode?.let { sb.appendLine("Exit Code: $it") }
            stderr?.let {
                sb.appendLine("Recent Stderr:")
                sb.appendLine(it.takeLast(1000))
            }
            if (recentLines.isNotEmpty()) {
                sb.appendLine("Recent Terminal Output (last ${recentLines.size} lines):")
                recentLines.takeLast(15).forEach { sb.appendLine(it) }
            }
            if (dirFiles.isNotEmpty()) {
                sb.appendLine("Files in Directory:")
                dirFiles.forEach { sb.appendLine("  $it") }
            }
        }
        sb.appendLine("User Query: $customPrompt")

        val formatted = sb.toString()
        val estimatedTokens = (formatted.length / 4.0).toInt().coerceAtLeast(1)

        return AiContextPayload(
            level = config.contextLevel,
            targetModel = targetModelName,
            workingDirectory = cwd,
            lastCommand = lastCmd,
            exitCode = exitCode,
            recentStderr = stderr,
            recentOutputLines = recentLines,
            directoryFiles = dirFiles,
            estimatedTokens = estimatedTokens,
            formattedContextText = formatted
        )
    }

    fun isDestructive(command: String): Pair<Boolean, String?> {
        val trimmed = command.trim()
        val destructivePatterns = listOf(
            Regex("""\brm\s+(-[a-zA-Z]*r[a-zA-Z]*f?|-f[a-zA-Z]*r)\s+.*""") to "Recursive force file deletion (rm -rf)",
            Regex("""\brm\s+-[a-zA-Z]*\s+/\s*""") to "Deletion of root or broad paths",
            Regex("""\bdd\s+if=.*""") to "Direct disk write via dd",
            Regex("""\bmkfs(\.[a-zA-Z0-9]+)?\s+.*""") to "Filesystem format (mkfs)",
            Regex("""\bchmod\s+-R\s+777\s+.*""") to "Insecure broad permission modification (chmod -R 777)",
            Regex("""\bchown\s+-R\s+.*""") to "Recursive ownership modification (chown -R)",
            Regex("""\breboot\b""") to "System reboot command",
            Regex("""\bformat\b""") to "Drive format command",
            Regex("""\bkillall\s+-9\s+.*""") to "Mass forceful termination of processes"
        )

        for ((pattern, desc) in destructivePatterns) {
            if (pattern.containsMatchIn(trimmed)) {
                return true to desc
            }
        }

        if (trimmed == "rm -rf *" || trimmed.startsWith("rm -rf /")) {
            return true to "Critical mass file deletion"
        }

        return false to null
    }
}
