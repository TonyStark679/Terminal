package com.example.command

enum class CommandCategory {
    TERMINAL,
    AI,
    FILE,
    GIT,
    VIEW,
    SETTINGS,
    AUTOMATION,
    SNIPPETS
}

data class PaletteAction(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val category: CommandCategory,
    val shortcut: String? = null,
    val iconName: String? = null,
    val onExecute: () -> Unit
)

object CommandPaletteCatalog {

    fun getDefaultActions(
        onNewTerminal: () -> Unit,
        onClearTerminal: () -> Unit,
        onToggleAiChat: () -> Unit,
        onToggleEditor: () -> Unit,
        onToggleFiles: () -> Unit,
        onToggleGit: () -> Unit,
        onToggleWorkflows: () -> Unit,
        onOpenThemes: () -> Unit,
        onOpenSettings: () -> Unit,
        onOpenSnippets: () -> Unit,
        onTriggerAiExplain: () -> Unit,
        onTriggerAiFix: () -> Unit,
        onTriggerGemmaToT: () -> Unit,
        onTriggerQwenReview: () -> Unit
    ): List<PaletteAction> {
        return listOf(
            // Terminal
            PaletteAction(
                id = "term.new",
                title = "New Terminal Session",
                subtitle = "Create a new shell tab",
                category = CommandCategory.TERMINAL,
                shortcut = "Ctrl+T",
                onExecute = onNewTerminal
            ),
            PaletteAction(
                id = "term.clear",
                title = "Clear Terminal Screen",
                subtitle = "Reset scrollback output",
                category = CommandCategory.TERMINAL,
                shortcut = "Ctrl+L",
                onExecute = onClearTerminal
            ),
            // AI
            PaletteAction(
                id = "ai.chat",
                title = "Open AI Assistant",
                subtitle = "Chat with Qwen / Ollama / Gemini",
                category = CommandCategory.AI,
                shortcut = "Ctrl+Space",
                onExecute = onToggleAiChat
            ),
            PaletteAction(
                id = "ai.explain_last",
                title = "AI Explain Last Error",
                subtitle = "Analyze failed command and stderr",
                category = CommandCategory.AI,
                shortcut = "Alt+E",
                onExecute = onTriggerAiExplain
            ),
            PaletteAction(
                id = "ai.fix",
                title = "AI Fix Command",
                subtitle = "Suggest corrected command syntax",
                category = CommandCategory.AI,
                shortcut = "Alt+F",
                onExecute = onTriggerAiFix
            ),
            PaletteAction(
                id = "ai.gemma_tot",
                title = "Gemma Tree-of-Thoughts Reasoning",
                subtitle = "Multi-candidate structured problem solver",
                category = CommandCategory.AI,
                shortcut = "Alt+T",
                onExecute = onTriggerGemmaToT
            ),
            PaletteAction(
                id = "ai.qwen_review",
                title = "Qwen Code Review & Diff",
                subtitle = "Review project files with unified diff proposal",
                category = CommandCategory.AI,
                shortcut = "Alt+Q",
                onExecute = onTriggerQwenReview
            ),
            // Files & Editor
            PaletteAction(
                id = "file.explorer",
                title = "Open File Explorer",
                subtitle = "Browse workspace and home directory",
                category = CommandCategory.FILE,
                shortcut = "Ctrl+O",
                onExecute = onToggleFiles
            ),
            PaletteAction(
                id = "file.editor",
                title = "Open Code Editor",
                subtitle = "Edit code files with syntax highlighting",
                category = CommandCategory.FILE,
                shortcut = "Ctrl+E",
                onExecute = onToggleEditor
            ),
            // Git
            PaletteAction(
                id = "git.panel",
                title = "Open Git Status & Commits",
                subtitle = "Visual branch, changes and commit history",
                category = CommandCategory.GIT,
                shortcut = "Ctrl+G",
                onExecute = onToggleGit
            ),
            // Automation
            PaletteAction(
                id = "auto.workflows",
                title = "Automation Workflows",
                subtitle = "Manage Triggers, Conditions and Actions",
                category = CommandCategory.AUTOMATION,
                shortcut = "Ctrl+W",
                onExecute = onToggleWorkflows
            ),
            // Snippets
            PaletteAction(
                id = "snippets.open",
                title = "Command Snippets & Bookmarks",
                subtitle = "Insert saved shell scripts and recipes",
                category = CommandCategory.SNIPPETS,
                shortcut = "Ctrl+S",
                onExecute = onOpenSnippets
            ),
            // Settings & View
            PaletteAction(
                id = "view.themes",
                title = "Themes & Appearance",
                subtitle = "Select from 60+ terminal themes",
                category = CommandCategory.SETTINGS,
                shortcut = "Ctrl+K T",
                onExecute = onOpenThemes
            ),
            PaletteAction(
                id = "settings.open",
                title = "Preferences & AI Configuration",
                subtitle = "Configure Ollama endpoints, models, font sizes",
                category = CommandCategory.SETTINGS,
                shortcut = "Ctrl+,",
                onExecute = onOpenSettings
            )
        )
    }
}
