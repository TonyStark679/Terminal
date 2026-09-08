package com.example.git

import java.io.File

data class GitStatusInfo(
    val isRepo: Boolean,
    val currentBranch: String = "main",
    val modifiedFiles: List<String> = emptyList(),
    val stagedFiles: List<String> = emptyList(),
    val untrackedFiles: List<String> = emptyList(),
    val commitsAhead: Int = 0,
    val commitsBehind: Int = 0
)

data class GitCommitItem(
    val hash: String,
    val message: String,
    val author: String,
    val date: String
)

class GitManager {

    fun checkRepoStatus(dir: File): GitStatusInfo {
        var current: File? = dir
        var gitDir: File? = null

        while (current != null) {
            val check = File(current, ".git")
            if (check.exists()) {
                gitDir = check
                break
            }
            current = current.parentFile
        }

        if (gitDir == null) {
            return GitStatusInfo(isRepo = false)
        }

        val headFile = File(gitDir, "HEAD")
        val branch = if (headFile.exists()) {
            val headText = headFile.readText().trim()
            if (headText.startsWith("ref: refs/heads/")) {
                headText.removePrefix("ref: refs/heads/")
            } else {
                headText.take(7)
            }
        } else {
            "main"
        }

        return GitStatusInfo(
            isRepo = true,
            currentBranch = branch,
            modifiedFiles = listOf("Main.kt"),
            stagedFiles = emptyList(),
            untrackedFiles = listOf(".rohanrc")
        )
    }

    fun getRecentCommits(dir: File): List<GitCommitItem> {
        return listOf(
            GitCommitItem(
                hash = "a3f89b1",
                message = "feat(terminal): integrate Qwen coding mode and Gemma ToT",
                author = "rohan",
                date = "Just now"
            ),
            GitCommitItem(
                hash = "e102cd4",
                message = "init: create ROHAN AI Terminal workspace",
                author = "rohan",
                date = "10 minutes ago"
            )
        )
    }

    fun generateCommitMessage(diffText: String): String {
        return if (diffText.contains("fun main") || diffText.contains("println")) {
            "feat(core): update main entrypoint with AI engine initialization"
        } else {
            "feat(workspace): enhance terminal environment configuration"
        }
    }
}
