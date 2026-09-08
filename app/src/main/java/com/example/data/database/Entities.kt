package com.example.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "command_history")
data class CommandHistory(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val command: String,
    val timestamp: Long = System.currentTimeMillis(),
    val exitCode: Int = 0,
    val workingDirectory: String = "",
    val isFavorite: Boolean = false,
    val sessionId: String = ""
)

@Entity(tableName = "command_snippets")
data class Snippet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val command: String,
    val description: String? = null,
    val category: String = "General",
    val tags: String = ""
)

@Entity(tableName = "terminal_profiles")
data class TerminalProfile(
    @PrimaryKey val id: String,
    val name: String,
    val shell: String = "/system/bin/sh",
    val workingDir: String = "",
    val startupCommands: String = "",
    val themeId: String = "tokyo_night",
    val aiProvider: String = "Ollama",
    val fontName: String = "JetBrains Mono",
    val extraKeys: String = "ESC,TAB,CTRL,ALT,HOME,END,↑,↓,←,→,/,~,|,$,-"
)

@Entity(tableName = "ai_conversations")
data class AiConversation(
    @PrimaryKey val id: String,
    val title: String,
    val createdAt: Long = System.currentTimeMillis(),
    val modelUsed: String = "Qwen 2.5"
)

@Entity(tableName = "ai_messages")
data class AiMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val conversationId: String,
    val role: String, // "user", "assistant", "system"
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val codeBlock: String? = null
)

@Entity(tableName = "automation_workflows")
data class AutomationWorkflow(
    @PrimaryKey val id: String,
    val name: String,
    val description: String = "",
    val triggerType: String = "ON_ERROR", // ON_ERROR, ON_SUCCESS, ON_PATTERN
    val actionType: String = "AI_EXPLAIN", // AI_EXPLAIN, EXECUTE_COMMAND, NOTIFY
    val definitionJson: String = "{}",
    val isEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)
