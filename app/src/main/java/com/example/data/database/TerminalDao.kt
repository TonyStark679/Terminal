package com.example.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TerminalDao {
    // History
    @Query("SELECT * FROM command_history ORDER BY timestamp DESC LIMIT 300")
    fun getAllHistory(): Flow<List<CommandHistory>>

    @Query("SELECT * FROM command_history WHERE command LIKE '%' || :query || '%' ORDER BY timestamp DESC LIMIT 100")
    fun searchHistory(query: String): Flow<List<CommandHistory>>

    @Query("SELECT * FROM command_history WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoriteHistory(): Flow<List<CommandHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: CommandHistory): Long

    @Query("UPDATE command_history SET isFavorite = :isFav WHERE id = :id")
    suspend fun setFavorite(id: Long, isFav: Boolean)

    @Query("DELETE FROM command_history WHERE id = :id")
    suspend fun deleteHistory(id: Long)

    @Query("DELETE FROM command_history")
    suspend fun clearHistory()

    // Snippets
    @Query("SELECT * FROM command_snippets ORDER BY category, title ASC")
    fun getAllSnippets(): Flow<List<Snippet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSnippet(snippet: Snippet): Long

    @Delete
    suspend fun deleteSnippet(snippet: Snippet)

    // Profiles
    @Query("SELECT * FROM terminal_profiles ORDER BY name ASC")
    fun getAllProfiles(): Flow<List<TerminalProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: TerminalProfile)

    @Query("SELECT * FROM terminal_profiles WHERE id = :id LIMIT 1")
    suspend fun getProfileById(id: String): TerminalProfile?

    // AI Conversations
    @Query("SELECT * FROM ai_conversations ORDER BY createdAt DESC")
    fun getAllConversations(): Flow<List<AiConversation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conv: AiConversation)

    @Query("DELETE FROM ai_conversations WHERE id = :id")
    suspend fun deleteConversation(id: String)

    @Query("SELECT * FROM ai_messages WHERE conversationId = :convId ORDER BY timestamp ASC")
    fun getMessagesForConversation(convId: String): Flow<List<AiMessage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(msg: AiMessage): Long

    // Workflows
    @Query("SELECT * FROM automation_workflows ORDER BY createdAt DESC")
    fun getAllWorkflows(): Flow<List<AutomationWorkflow>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkflow(workflow: AutomationWorkflow)

    @Update
    suspend fun updateWorkflow(workflow: AutomationWorkflow)

    @Query("DELETE FROM automation_workflows WHERE id = :id")
    suspend fun deleteWorkflow(id: String)
}

@Database(
    entities = [
        CommandHistory::class,
        Snippet::class,
        TerminalProfile::class,
        AiConversation::class,
        AiMessage::class,
        AutomationWorkflow::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun terminalDao(): TerminalDao
}
