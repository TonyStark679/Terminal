package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ai.model.*
import com.example.automation.WorkflowEngine
import com.example.automation.WorkflowExecutionLog
import com.example.command.CommandPaletteCatalog
import com.example.command.PaletteAction
import com.example.data.database.AutomationWorkflow
import com.example.data.database.CommandHistory
import com.example.data.database.Snippet
import com.example.data.repository.TerminalRepository
import com.example.editor.EditorDocument
import com.example.editor.FileManager
import com.example.editor.FileItem
import com.example.git.GitCommitItem
import com.example.git.GitManager
import com.example.git.GitStatusInfo
import com.example.plugin.PluginManager
import com.example.plugin.TerminalPlugin
import com.example.terminal.engine.*
import com.example.theme.model.TerminalTheme
import com.example.theme.model.ThemeCatalog
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

enum class AppScreen {
    TERMINAL,
    EDITOR,
    FILES,
    GIT,
    WORKFLOWS,
    THEMES,
    SETTINGS,
    SNIPPETS,
    HISTORY,
    PLUGINS
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repository = TerminalRepository(application)
    val shellEnvironment = ShellEnvironment(application)
    val fileManager = FileManager()
    val gitManager = GitManager()
    val pluginManager = PluginManager()
    val aiService = AiService()
    val qwenCodingEngine = QwenCodingEngine(aiService)
    val gemmaToTEngine = GemmaToTEngine(aiService)

    // Current Theme
    private val _currentTheme = MutableStateFlow(ThemeCatalog.allThemes.first())
    val currentTheme: StateFlow<TerminalTheme> = _currentTheme.asStateFlow()

    // Navigation & Modals
    private val _currentScreen = MutableStateFlow(AppScreen.TERMINAL)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val _isCommandPaletteOpen = MutableStateFlow(false)
    val isCommandPaletteOpen: StateFlow<Boolean> = _isCommandPaletteOpen.asStateFlow()

    private val _isAiChatSheetOpen = MutableStateFlow(false)
    val isAiChatSheetOpen: StateFlow<Boolean> = _isAiChatSheetOpen.asStateFlow()

    private val _isAiInspectionDialogOpen = MutableStateFlow(false)
    val isAiInspectionDialogOpen: StateFlow<Boolean> = _isAiInspectionDialogOpen.asStateFlow()

    private val _activeAiContextPayload = MutableStateFlow<AiContextPayload?>(null)
    val activeAiContextPayload: StateFlow<AiContextPayload?> = _activeAiContextPayload.asStateFlow()

    private val _aiCommandProposal = MutableStateFlow<AiCommandProposal?>(null)
    val aiCommandProposal: StateFlow<AiCommandProposal?> = _aiCommandProposal.asStateFlow()

    private val _isDestructiveConfirmOpen = MutableStateFlow(false)
    val isDestructiveConfirmOpen: StateFlow<Boolean> = _isDestructiveConfirmOpen.asStateFlow()

    private val _gemmaToTResult = MutableStateFlow<GemmaToTResult?>(null)
    val gemmaToTResult: StateFlow<GemmaToTResult?> = _gemmaToTResult.asStateFlow()

    private val _qwenReviewResult = MutableStateFlow<QwenReviewResult?>(null)
    val qwenReviewResult: StateFlow<QwenReviewResult?> = _qwenReviewResult.asStateFlow()

    // AI Configuration State
    private val _aiConfig = MutableStateFlow(AiModelConfig())
    val aiConfig: StateFlow<AiModelConfig> = _aiConfig.asStateFlow()

    // AI Chat History
    private val _aiMessages = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val aiMessages: StateFlow<List<Pair<String, String>>> = _aiMessages.asStateFlow()

    // Editor State
    private val _editorDocument = MutableStateFlow<EditorDocument?>(null)
    val editorDocument: StateFlow<EditorDocument?> = _editorDocument.asStateFlow()

    // File Explorer State
    private val _currentDirectory = MutableStateFlow<File>(shellEnvironment.homeDir)
    val currentDirectory: StateFlow<File> = _currentDirectory.asStateFlow()

    private val _directoryFiles = MutableStateFlow<List<FileItem>>(emptyList())
    val directoryFiles: StateFlow<List<FileItem>> = _directoryFiles.asStateFlow()

    private val _showHiddenFiles = MutableStateFlow(false)
    val showHiddenFiles: StateFlow<Boolean> = _showHiddenFiles.asStateFlow()

    // Git State
    private val _gitStatus = MutableStateFlow<GitStatusInfo>(GitStatusInfo(isRepo = false))
    val gitStatus: StateFlow<GitStatusInfo> = _gitStatus.asStateFlow()

    private val _gitCommits = MutableStateFlow<List<GitCommitItem>>(emptyList())
    val gitCommits: StateFlow<List<GitCommitItem>> = _gitCommits.asStateFlow()

    // Extra Keyboard Modifiers
    private val _isCtrlPressed = MutableStateFlow(false)
    val isCtrlPressed: StateFlow<Boolean> = _isCtrlPressed.asStateFlow()

    private val _isAltPressed = MutableStateFlow(false)
    val isAltPressed: StateFlow<Boolean> = _isAltPressed.asStateFlow()

    // Font size & Display preferences
    private val _terminalFontSize = MutableStateFlow(13)
    val terminalFontSize: StateFlow<Int> = _terminalFontSize.asStateFlow()

    // Automation Engine
    val workflowEngine: WorkflowEngine by lazy {
        WorkflowEngine(
            onExecuteInTerminal = { cmd -> sessionManager.currentSession?.executeCommand(cmd) },
            onAiQuery = { prompt -> aiService.queryAi(prompt, _aiConfig.value) }
        )
    }

    // Workflows from Room
    val workflowsFlow: StateFlow<List<AutomationWorkflow>> = repository.getAllWorkflows()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Command History & Snippets
    val commandHistoryFlow: StateFlow<List<CommandHistory>> = repository.getAllHistory()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val snippetsFlow: StateFlow<List<Snippet>> = repository.getAllSnippets()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Terminal Session Manager
    val sessionManager: SessionManager by lazy {
        SessionManager(
            context = application,
            shellEnvironment = shellEnvironment,
            currentTheme = _currentTheme.value,
            onCommandFinished = { cmd, exitCode, dir, sessId ->
                handleCommandFinished(cmd, exitCode, dir, sessId)
            },
            onAiCommandTriggered = { cmd, sess ->
                handleAiCommandFromTerminal(cmd, sess)
            },
            onEditCommandTriggered = { filePath ->
                openFileInEditor(File(filePath))
            }
        )
    }

    init {
        sessionManager.initialize()
        refreshDirectoryFiles()
        refreshGitStatus()
    }

    fun navigateTo(screen: AppScreen) {
        _currentScreen.value = screen
    }

    fun setCommandPaletteOpen(open: Boolean) {
        _isCommandPaletteOpen.value = open
    }

    fun setAiChatOpen(open: Boolean) {
        _isAiChatSheetOpen.value = open
    }

    fun setAiInspectionOpen(open: Boolean) {
        _isAiInspectionDialogOpen.value = open
    }

    fun toggleCtrl() {
        _isCtrlPressed.value = !_isCtrlPressed.value
    }

    fun toggleAlt() {
        _isAltPressed.value = !_isAltPressed.value
    }

    fun setFontSize(size: Int) {
        _terminalFontSize.value = size.coerceIn(10, 24)
    }

    fun setTheme(theme: TerminalTheme) {
        _currentTheme.value = theme
        sessionManager.updateTheme(theme)
    }

    fun updateAiConfig(newConfig: AiModelConfig) {
        _aiConfig.value = newConfig
    }

    fun executeKeyAction(key: String) {
        val session = sessionManager.currentSession ?: return
        if (_isCtrlPressed.value) {
            _isCtrlPressed.value = false
            when (key.uppercase()) {
                "C" -> session.sendCtrlC()
                "D" -> session.sendCtrlD()
                "L" -> session.clear()
                "Z" -> session.write("\u001A")
                "A" -> session.write("\u0001")
                "E" -> session.write("\u0005")
                "K" -> session.write("\u000B")
                "U" -> session.write("\u0015")
                "W" -> session.write("\u0017")
                else -> session.write(key)
            }
        } else {
            session.sendKey(key)
        }
    }

    private fun handleCommandFinished(command: String, exitCode: Int, workingDir: String, sessionId: String) {
        viewModelScope.launch {
            repository.insertHistory(
                CommandHistory(
                    command = command,
                    workingDirectory = workingDir,
                    exitCode = exitCode,
                    sessionId = sessionId
                )
            )
            workflowEngine.onCommandExecuted(command, exitCode, workingDir, workflowsFlow.value)
        }
    }

    private fun handleAiCommandFromTerminal(rawCommand: String, session: TerminalSession) {
        viewModelScope.launch {
            val trimmed = rawCommand.trim()
            val lower = trimmed.lowercase()

            if (lower.startsWith("ai explain-last") || lower == "ai fix" || lower.startsWith("ai explain")) {
                explainLastError(session)
            } else if (lower.startsWith("tot ") || lower.startsWith("gemma-tot ")) {
                val problem = trimmed.substringAfter(" ").trim()
                runGemmaToT(problem)
            } else if (lower.startsWith("qwen review ") || lower == "qwen review") {
                val path = trimmed.substringAfter("review").trim().ifEmpty { session.workingDirectoryFlow.value }
                runQwenReview(path)
            } else {
                val query = trimmed.removePrefix("/ai").removePrefix("ai").removePrefix("qwen").trim()
                sendAiMessage(query.ifEmpty { "Explain current terminal environment" })
            }
        }
    }

    fun explainLastError(session: TerminalSession? = sessionManager.currentSession) {
        viewModelScope.launch {
            val sess = session ?: return@launch
            val contextPayload = AiContextBuilder.buildContext(
                session = sess,
                config = _aiConfig.value,
                customPrompt = "Explain why this command failed and suggest fix: ${sess.lastCommand}",
                targetModelName = _aiConfig.value.ollamaModel
            )
            _activeAiContextPayload.value = contextPayload

            val prompt = "Command failed with code ${sess.lastExitCode}:\nCommand: ${sess.lastCommand}\nStderr: ${sess.recentStderr}\nExplain cause and provide the exact fix."
            val response = aiService.queryAi(prompt, _aiConfig.value)

            val proposalCommand = if (sess.lastCommand.startsWith("ls") || sess.recentStderr.contains("No such file")) {
                "mkdir -p \"${sess.workingDirectoryFlow.value}/workspace\" && cd \"${sess.workingDirectoryFlow.value}/workspace\""
            } else {
                "${sess.lastCommand} --help"
            }

            val (isDestructive, warning) = AiContextBuilder.isDestructive(proposalCommand)
            _aiCommandProposal.value = AiCommandProposal(
                command = proposalCommand,
                explanation = response,
                isDestructive = isDestructive,
                safetyWarning = warning
            )
            _isAiChatSheetOpen.value = true
        }
    }

    fun sendAiMessage(userText: String) {
        if (userText.isBlank()) return
        val currentList = _aiMessages.value.toMutableList()
        currentList.add("user" to userText)
        _aiMessages.value = currentList

        viewModelScope.launch {
            val session = sessionManager.currentSession
            val contextPayload = AiContextBuilder.buildContext(
                session = session,
                config = _aiConfig.value,
                customPrompt = userText,
                targetModelName = _aiConfig.value.ollamaModel
            )
            _activeAiContextPayload.value = contextPayload

            val aiResponse = aiService.queryAi(contextPayload.formattedContextText, _aiConfig.value)
            val updated = _aiMessages.value.toMutableList()
            updated.add("ai" to aiResponse)
            _aiMessages.value = updated

            // Check if response contains a recommended command
            val codeBlockRegex = Regex("""```(?:bash|sh)?\s*([\s\S]*?)```""")
            val match = codeBlockRegex.find(aiResponse)
            if (match != null) {
                val candidateCmd = match.groupValues[1].trim()
                if (candidateCmd.isNotEmpty() && !candidateCmd.contains("\n")) {
                    val (isDestr, warning) = AiContextBuilder.isDestructive(candidateCmd)
                    _aiCommandProposal.value = AiCommandProposal(
                        command = candidateCmd,
                        explanation = "Suggested command from AI Assistant",
                        isDestructive = isDestr,
                        safetyWarning = warning
                    )
                }
            }
        }
    }

    fun runGemmaToT(problem: String, mode: String = "plan") {
        viewModelScope.launch {
            val res = gemmaToTEngine.executeToT(problem, mode, _aiConfig.value)
            _gemmaToTResult.value = res
            _currentScreen.value = AppScreen.TERMINAL
            _isAiChatSheetOpen.value = true
        }
    }

    fun runQwenReview(path: String) {
        viewModelScope.launch {
            val res = qwenCodingEngine.reviewFileOrDirectory(path, _aiConfig.value)
            _qwenReviewResult.value = res
            _currentScreen.value = AppScreen.TERMINAL
            _isAiChatSheetOpen.value = true
        }
    }

    fun executeProposedCommand(proposal: AiCommandProposal) {
        if (proposal.isDestructive) {
            _isDestructiveConfirmOpen.value = true
            return
        }
        applyProposedCommand(proposal.command)
    }

    fun confirmDestructiveExecution() {
        _isDestructiveConfirmOpen.value = false
        _aiCommandProposal.value?.let { applyProposedCommand(it.command) }
    }

    fun dismissDestructiveExecution() {
        _isDestructiveConfirmOpen.value = false
    }

    private fun applyProposedCommand(cmd: String) {
        sessionManager.currentSession?.executeCommand(cmd)
        _aiCommandProposal.value = null
        _isAiChatSheetOpen.value = false
        _currentScreen.value = AppScreen.TERMINAL
    }

    // File Explorer & Editor Actions
    fun openDirectory(dir: File) {
        if (dir.exists() && dir.isDirectory) {
            _currentDirectory.value = dir
            refreshDirectoryFiles()
            refreshGitStatus()
        }
    }

    fun toggleHiddenFiles() {
        _showHiddenFiles.value = !_showHiddenFiles.value
        refreshDirectoryFiles()
    }

    fun refreshDirectoryFiles() {
        _directoryFiles.value = fileManager.listDirectory(_currentDirectory.value, _showHiddenFiles.value)
    }

    fun openFileInEditor(file: File) {
        val content = fileManager.readFile(file)
        _editorDocument.value = EditorDocument(
            file = file,
            content = content,
            language = EditorDocument.detectLanguage(file.name)
        )
        _currentScreen.value = AppScreen.EDITOR
    }

    fun updateEditorContent(newContent: String) {
        _editorDocument.value = _editorDocument.value?.copy(content = newContent, isDirty = true)
    }

    fun saveEditorFile() {
        val doc = _editorDocument.value ?: return
        fileManager.writeFile(doc.file, doc.content)
        _editorDocument.value = doc.copy(isDirty = false)
    }

    fun refreshGitStatus() {
        val dir = sessionManager.currentSession?.workingDirectoryFlow?.value?.let { File(it) } ?: _currentDirectory.value
        _gitStatus.value = gitManager.checkRepoStatus(dir)
        _gitCommits.value = gitManager.getRecentCommits(dir)
    }

    fun toggleWorkflow(workflow: AutomationWorkflow) {
        viewModelScope.launch {
            repository.updateWorkflow(workflow.copy(isEnabled = !workflow.isEnabled))
        }
    }

    fun togglePlugin(pluginId: String) {
        pluginManager.togglePlugin(pluginId)
    }

    fun getCommandPaletteActions(): List<PaletteAction> {
        return CommandPaletteCatalog.getDefaultActions(
            onNewTerminal = {
                sessionManager.createNewSession()
                _currentScreen.value = AppScreen.TERMINAL
            },
            onClearTerminal = {
                sessionManager.currentSession?.clear()
            },
            onToggleAiChat = {
                _isAiChatSheetOpen.value = !_isAiChatSheetOpen.value
            },
            onToggleEditor = {
                _currentScreen.value = AppScreen.EDITOR
            },
            onToggleFiles = {
                _currentScreen.value = AppScreen.FILES
            },
            onToggleGit = {
                refreshGitStatus()
                _currentScreen.value = AppScreen.GIT
            },
            onToggleWorkflows = {
                _currentScreen.value = AppScreen.WORKFLOWS
            },
            onOpenThemes = {
                _currentScreen.value = AppScreen.THEMES
            },
            onOpenSettings = {
                _currentScreen.value = AppScreen.SETTINGS
            },
            onOpenSnippets = {
                _currentScreen.value = AppScreen.SNIPPETS
            },
            onTriggerAiExplain = {
                explainLastError()
            },
            onTriggerAiFix = {
                explainLastError()
            },
            onTriggerGemmaToT = {
                runGemmaToT("Evaluate system architecture trade-offs for Android terminal emulator")
            },
            onTriggerQwenReview = {
                val cwd = sessionManager.currentSession?.workingDirectoryFlow?.value ?: shellEnvironment.homeDir.absolutePath
                runQwenReview(cwd)
            }
        )
    }
}
