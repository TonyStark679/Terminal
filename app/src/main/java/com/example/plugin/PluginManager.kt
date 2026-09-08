package com.example.plugin

data class TerminalPlugin(
    val id: String,
    val name: String,
    val version: String,
    val author: String,
    val description: String,
    val category: String,
    val isEnabled: Boolean = true,
    val providedCommands: List<String> = emptyList(),
    val documentation: String
)

class PluginManager {

    private val plugins = mutableListOf(
        TerminalPlugin(
            id = "git-enhancer",
            name = "Git Enhancer",
            version = "1.2.0",
            author = "ROHAN Core",
            description = "Smart visual git status, AI commit generator, branch switching, and diff analyzer.",
            category = "Version Control",
            isEnabled = true,
            providedCommands = listOf("gs", "git-ai-commit", "gdiff", "gbranch"),
            documentation = "Provides rapid Git utilities and AI-assisted commit generation from diffs."
        ),
        TerminalPlugin(
            id = "system-monitor",
            name = "System Monitor & Top",
            version = "1.0.0",
            author = "ROHAN Core",
            description = "Real-time process monitor, RAM usage, storage breakdown, and battery diagnostics.",
            category = "System",
            isEnabled = true,
            providedCommands = listOf("sysmon", "meminfo", "battery", "procs"),
            documentation = "Reads /proc/meminfo, /proc/stat, and Android battery service to display live metrics."
        ),
        TerminalPlugin(
            id = "network-tools",
            name = "Network Toolkit",
            version = "1.1.0",
            author = "ROHAN Core",
            description = "Ping, DNS lookup, IP route inspector, port check, and interactive cURL request builder.",
            category = "Networking",
            isEnabled = true,
            providedCommands = listOf("curl-builder", "myip", "netscan", "dns"),
            documentation = "Assists with network diagnostics inside the Android environment."
        ),
        TerminalPlugin(
            id = "python-env",
            name = "Python & Script Runner",
            version = "1.0.0",
            author = "ROHAN Core",
            description = "Environment detector, script runner, and syntax validator for Python/Shell scripts.",
            category = "Development",
            isEnabled = true,
            providedCommands = listOf("pyrun", "pylint", "shlint"),
            documentation = "Wraps script execution with stdout inspection and AI code error explanation."
        ),
        TerminalPlugin(
            id = "docker-helper",
            name = "Container & Remote Docker",
            version = "1.0.0",
            author = "ROHAN Core",
            description = "Docker CLI syntax generator, compose templates, and remote Docker daemon connector.",
            category = "DevOps",
            isEnabled = false,
            providedCommands = listOf("docker-ai", "compose-gen"),
            documentation = "Generates production Dockerfile and docker-compose configurations."
        )
    )

    fun getInstalledPlugins(): List<TerminalPlugin> = plugins.toList()

    fun togglePlugin(pluginId: String): Boolean {
        val idx = plugins.indexOfFirst { it.id == pluginId }
        if (idx != -1) {
            val p = plugins[idx]
            val updated = p.copy(isEnabled = !p.isEnabled)
            plugins[idx] = updated
            return updated.isEnabled
        }
        return false
    }
}
