package com.example.terminal.engine

import android.content.Context
import com.example.theme.model.TerminalTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.util.UUID

class SessionManager(
    private val context: Context,
    private val shellEnvironment: ShellEnvironment,
    private var currentTheme: TerminalTheme,
    private val onCommandFinished: ((command: String, exitCode: Int, workingDir: String, sessionId: String) -> Unit)? = null,
    private val onAiCommandTriggered: ((command: String, session: TerminalSession) -> Unit)? = null,
    private val onEditCommandTriggered: ((filePath: String) -> Unit)? = null
) {
    private val _sessions = MutableStateFlow<List<TerminalSession>>(emptyList())
    val sessions: StateFlow<List<TerminalSession>> = _sessions.asStateFlow()

    private val _activeSessionIndex = MutableStateFlow(0)
    val activeSessionIndex: StateFlow<Int> = _activeSessionIndex.asStateFlow()

    val currentSession: TerminalSession?
        get() {
            val list = _sessions.value
            val idx = _activeSessionIndex.value
            return if (list.isNotEmpty() && idx in list.indices) list[idx] else null
        }

    fun initialize() {
        shellEnvironment.initializeEnvironment()
        if (_sessions.value.isEmpty()) {
            createNewSession("Terminal 1")
        }
    }

    fun createNewSession(customTitle: String? = null, initialDir: File? = null): TerminalSession {
        val count = _sessions.value.size + 1
        val title = customTitle ?: "Terminal $count"
        val session = TerminalSession(
            id = UUID.randomUUID().toString(),
            title = title,
            initialWorkingDir = initialDir ?: shellEnvironment.homeDir,
            shellEnvironment = shellEnvironment,
            initialTheme = currentTheme,
            onCommandFinished = onCommandFinished,
            onAiCommandTriggered = onAiCommandTriggered,
            onEditCommandTriggered = onEditCommandTriggered
        )
        session.start()

        val updated = _sessions.value.toMutableList().apply { add(session) }
        _sessions.value = updated
        _activeSessionIndex.value = updated.size - 1
        return session
    }

    fun selectSession(index: Int) {
        if (index in _sessions.value.indices) {
            _activeSessionIndex.value = index
        }
    }

    fun renameSession(index: Int, newTitle: String) {
        val list = _sessions.value
        if (index in list.indices && newTitle.isNotBlank()) {
            list[index].setTitle(newTitle.trim())
        }
    }

    fun duplicateSession(index: Int): TerminalSession? {
        val list = _sessions.value
        if (index !in list.indices) return null
        val source = list[index]
        val targetDir = File(source.workingDirectoryFlow.value)
        return createNewSession("${source.titleFlow.value} (Copy)", targetDir)
    }

    fun killSession(index: Int) {
        val list = _sessions.value.toMutableList()
        if (index in list.indices) {
            val removed = list.removeAt(index)
            removed.destroy()
            if (list.isEmpty()) {
                _sessions.value = emptyList()
                // Always have at least 1 session
                createNewSession("Terminal 1")
            } else {
                _sessions.value = list
                val newActive = (_activeSessionIndex.value - 1).coerceAtLeast(0).coerceAtMost(list.size - 1)
                _activeSessionIndex.value = newActive
            }
        }
    }

    fun updateTheme(theme: TerminalTheme) {
        currentTheme = theme
        _sessions.value.forEach { it.updateTheme(theme) }
    }

    fun destroyAll() {
        _sessions.value.forEach { it.destroy() }
        _sessions.value = emptyList()
    }
}
