package com.example.data.repository

import android.content.Context
import androidx.room.Room
import com.example.data.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TerminalRepository(private val dao: TerminalDao) {

    constructor(context: Context) : this(
        Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "rohan_terminal.db"
        )
            .fallbackToDestructiveMigration()
            .build()
            .terminalDao()
    ) {
        seedDefaults()
    }

    fun getAllHistory(): Flow<List<CommandHistory>> = dao.getAllHistory()
    fun getFavoriteHistory(): Flow<List<CommandHistory>> = dao.getFavoriteHistory()
    fun getAllSnippets(): Flow<List<Snippet>> = dao.getAllSnippets()
    fun getAllProfiles(): Flow<List<TerminalProfile>> = dao.getAllProfiles()
    fun getAllConversations(): Flow<List<AiConversation>> = dao.getAllConversations()
    fun getAllWorkflows(): Flow<List<AutomationWorkflow>> = dao.getAllWorkflows()

    fun searchHistory(query: String): Flow<List<CommandHistory>> = dao.searchHistory(query)
    fun getMessages(convId: String): Flow<List<AiMessage>> = dao.getMessagesForConversation(convId)

    suspend fun insertHistory(item: CommandHistory) {
        if (item.command.isBlank()) return
        dao.insertHistory(item)
    }

    suspend fun toggleFavorite(id: Long, currentFav: Boolean) = dao.setFavorite(id, !currentFav)
    suspend fun deleteHistory(id: Long) = dao.deleteHistory(id)
    suspend fun clearHistory() = dao.clearHistory()

    suspend fun insertSnippet(snippet: Snippet) = dao.insertSnippet(snippet)
    suspend fun deleteSnippet(snippet: Snippet) = dao.deleteSnippet(snippet)

    suspend fun saveProfile(profile: TerminalProfile) = dao.insertProfile(profile)
    suspend fun getProfile(id: String) = dao.getProfileById(id)

    suspend fun saveConversation(conv: AiConversation) = dao.insertConversation(conv)
    suspend fun deleteConversation(id: String) = dao.deleteConversation(id)
    suspend fun addMessage(msg: AiMessage) = dao.insertMessage(msg)

    suspend fun insertWorkflow(wf: AutomationWorkflow) = dao.insertWorkflow(wf)
    suspend fun updateWorkflow(wf: AutomationWorkflow) = dao.updateWorkflow(wf)
    suspend fun deleteWorkflow(id: String) = dao.deleteWorkflow(id)

    private fun seedDefaults() {
        CoroutineScope(Dispatchers.IO).launch {
            // Seed default profiles
            dao.insertProfile(
                TerminalProfile(
                    id = "default",
                    name = "Default Shell",
                    shell = "/system/bin/sh",
                    startupCommands = "echo 'Welcome to ROHAN AI Terminal v1.0'; uname -a",
                    themeId = "tokyo_night"
                )
            )

            // Seed common snippets
            dao.insertSnippet(
                Snippet(
                    title = "Process Tree",
                    command = "ps -ef || ps",
                    description = "List all active processes on the Android system",
                    category = "System",
                    tags = "ps,processes"
                )
            )
            dao.insertSnippet(
                Snippet(
                    title = "Disk Usage",
                    command = "df -h",
                    description = "Inspect storage partitions and free space",
                    category = "System",
                    tags = "df,disk,storage"
                )
            )
            dao.insertSnippet(
                Snippet(
                    title = "Find Large Files",
                    command = "find . -type f -size +10M",
                    description = "Find files larger than 10MB in current folder",
                    category = "Files",
                    tags = "find,size"
                )
            )
            dao.insertSnippet(
                Snippet(
                    title = "AI Last Error Fix",
                    command = "ai fix",
                    description = "Automatically analyze recent stderr and suggest fix",
                    category = "AI",
                    tags = "ai,fix"
                )
            )
            dao.insertSnippet(
                Snippet(
                    title = "Gemma ToT Plan",
                    command = "tot plan \"Project architecture planning\"",
                    description = "Run Gemma Tree-of-Thoughts reasoning for plan",
                    category = "AI",
                    tags = "tot,gemma,reasoning"
                )
            )

            // Seed default workflows
            dao.insertWorkflow(
                AutomationWorkflow(
                    id = "auto_explain_error",
                    name = "Auto Explain Error",
                    description = "Automatically queries AI when a command fails with non-zero exit code.",
                    triggerType = "ON_ERROR",
                    actionType = "AI_EXPLAIN",
                    isEnabled = true
                )
            )
            dao.insertWorkflow(
                AutomationWorkflow(
                    id = "auto_git_status",
                    name = "Auto Git Status on Git Push",
                    description = "Checks status after successful git commands.",
                    triggerType = "ON_PATTERN",
                    actionType = "EXECUTE_COMMAND",
                    definitionJson = "{\"pattern\":\"git push\",\"command\":\"git status\"}",
                    isEnabled = true
                )
            )
        }
    }
}
