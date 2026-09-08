package com.example.terminal.engine

import android.content.Context
import java.io.File

class ShellEnvironment(private val context: Context) {

    val homeDir: File by lazy {
        File(context.filesDir, "home").apply {
            if (!exists()) mkdirs()
        }
    }

    val binDir: File by lazy {
        File(homeDir, "bin").apply {
            if (!exists()) mkdirs()
        }
    }

    val termuxUsrBin = File("/data/data/com.termux/files/usr/bin")
    val hasTermuxBridge: Boolean get() = termuxUsrBin.exists() && termuxUsrBin.canExecute()

    fun initializeEnvironment() {
        // Create sample project directories in home
        val workspaceDir = File(homeDir, "workspace").apply { if (!exists()) mkdirs() }
        val sampleProject = File(workspaceDir, "demo-project").apply { if (!exists()) mkdirs() }
        val sampleFile = File(sampleProject, "Main.kt")
        if (!sampleFile.exists()) {
            sampleFile.writeText(
                """// ROHAN AI Terminal - Kotlin Demo
fun main() {
    println("Hello from ROHAN AI Terminal on Android!")
    val engine = "Qwen + Gemma ToT"
    println("AI Systems Active: ${'$'}engine")
}
"""
            )
        }

        // Install .profile / .rohanrc
        val profileFile = File(homeDir, ".profile")
        if (!profileFile.exists()) {
            profileFile.writeText(
                """# ROHAN AI Terminal Profile
export TERM=xterm-256color
export LANG=en_US.UTF-8
export USER=rohan
export ROHAN_VERSION=1.0.0
export PS1='\[\033[01;32m\]rohan@android\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
alias ll='ls -la'
alias la='ls -A'
alias gs='git status 2>/dev/null || echo "Not a git repository"'
"""
            )
        }

        // Generate shell helper scripts in binDir
        installScript(
            "ai",
            """#!/system/bin/sh
echo "\033[1;36m[ROHAN AI ASSISTANT]\033[0m"
if [ "${'$'}1" = "explain" ] || [ "${'$'}1" = "explain-last" ] || [ "${'$'}1" = "fix" ] || [ "${'$'}1" = "ask" ] || [ "${'$'}1" = "models" ]; then
    echo "AI CLI command dispatched to terminal engine: $*"
    echo "Tip: You can also tap the floating AI button or use /ai for immediate interactive cards."
else
    echo "Usage: ai <explain|explain-last|fix|ask|summarize|inspect|suggest|debug|models|use|workflow>"
    echo "Examples:"
    echo "  ai explain ls -la"
    echo "  ai fix"
    echo "  ai ask \"How do I find large files?\""
fi
"""
        )

        installScript(
            "qwen",
            """#!/system/bin/sh
echo "\033[1;35m[QWEN CODING ENGINE]\033[0m"
if [ -z "${'$'}1" ]; then
    echo "Usage: qwen <ask|explain|review|fix|refactor|test|generate> [file/args]"
    echo "Examples:"
    echo "  qwen review src/Main.kt"
    echo "  qwen fix error.log"
    echo "  qwen explain \"Why did the build fail?\""
else
    echo "Qwen coding analysis invoked for: $*"
    echo "Interactive review card active in ROHAN Terminal."
fi
"""
        )

        installScript(
            "tot",
            """#!/system/bin/sh
echo "\033[1;32m[GEMMA TREE-OF-THOUGHTS REASONING]\033[0m"
if [ -z "${'$'}1" ]; then
    echo "Usage: tot [solve|compare|plan|debug|architecture|optimize] <problem/question>"
    echo "Example: tot \"How should I structure this Kotlin project?\""
else
    echo "Executing Gemma Tree-of-Thoughts reasoning for: $*"
fi
"""
        )

        installScript(
            "pkg",
            """#!/system/bin/sh
echo "\033[1;34m[ROHAN ENVIRONMENT & PACKAGES]\033[0m"
echo "System Backend: Android Bionic Shell (toybox/toolbox)"
echo "Architecture: $(uname -m 2>/dev/null || echo 'Linux ARM64/x86_64')"
echo "Kernel: $(uname -r 2>/dev/null || echo 'Android Linux')"
echo "Core Utilities Available in PATH: ls, cat, grep, find, ps, df, mkdir, cp, mv, rm, touch, chmod, echo, date, uname, env"
if [ -d "/data/data/com.termux/files/usr/bin" ]; then
    echo "\033[1;32mTermux Bridge: Detected at /data/data/com.termux/files/usr/bin\033[0m"
else
    echo "\033[1;33mTermux Bridge: Termux not installed. Native Android Bionic environment active.\033[0m"
fi
"""
        )

        installScript(
            "project",
            """#!/system/bin/sh
echo "\033[1;33m[PROJECT SCANNER]\033[0m"
echo "Current directory: $(pwd)"
echo "Scanning for Git, Kotlin, Java, Python, Node, Gradle, CMake, and Shell files..."
ls -la
"""
        )
    }

    private fun installScript(name: String, content: String) {
        val file = File(binDir, name)
        file.writeText(content)
        file.setExecutable(true, false)
        file.setReadable(true, false)
    }

    fun getEnvironmentMap(customEnv: Map<String, String> = emptyMap()): Map<String, String> {
        val env = HashMap<String, String>()
        val pathBuilder = StringBuilder()
        pathBuilder.append(binDir.absolutePath)
        pathBuilder.append(":/system/bin:/system/xbin:/apex/com.android.runtime/bin")
        if (hasTermuxBridge) {
            pathBuilder.append(":").append(termuxUsrBin.absolutePath)
        }
        val currentPath = System.getenv("PATH")
        if (!currentPath.isNullOrBlank()) {
            pathBuilder.append(":").append(currentPath)
        }

        env["PATH"] = pathBuilder.toString()
        env["HOME"] = homeDir.absolutePath
        env["USER"] = "rohan"
        env["TERM"] = "xterm-256color"
        env["SHELL"] = "/system/bin/sh"
        env["LANG"] = "en_US.UTF-8"
        env["ROHAN_VERSION"] = "1.0.0"
        env.putAll(customEnv)
        return env
    }
}
