package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import com.example.ui.ai.AiChatSheet
import com.example.ui.ai.AiInspectionDialog
import com.example.ui.ai.DestructiveCommandDialog
import com.example.ui.automation.WorkflowsScreen
import com.example.ui.command.CommandPaletteDialog
import com.example.ui.editor.EditorScreen
import com.example.ui.files.FileExplorerScreen
import com.example.ui.git.GitScreen
import com.example.ui.plugins.PluginsScreen
import com.example.ui.settings.SettingsScreen
import com.example.ui.snippets.SnippetsScreen
import com.example.ui.terminal.TerminalScreen
import com.example.ui.theme.ThemeCatalogScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val theme by viewModel.currentTheme.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()
            val isCommandPaletteOpen by viewModel.isCommandPaletteOpen.collectAsState()
            val isAiChatOpen by viewModel.isAiChatSheetOpen.collectAsState()
            val isAiInspectionOpen by viewModel.isAiInspectionDialogOpen.collectAsState()
            val isDestructiveOpen by viewModel.isDestructiveConfirmOpen.collectAsState()
            val aiProposal by viewModel.aiCommandProposal.collectAsState()
            val activeContextPayload by viewModel.activeAiContextPayload.collectAsState()

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        drawerContainerColor = theme.surface,
                        drawerContentColor = theme.foreground
                    ) {
                        // Drawer Header
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(theme.background)
                                .padding(20.dp)
                        ) {
                            Text(
                                text = "ROHAN AI TERMINAL",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                color = theme.accent,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "AI-First Terminal Emulator v1.0.0",
                                fontSize = 11.sp,
                                color = theme.foreground.copy(alpha = 0.6f)
                            )
                            Text(
                                text = "Bionic Shell • Qwen • Gemma ToT",
                                fontSize = 10.sp,
                                fontFamily = FontFamily.Monospace,
                                color = theme.aiIndicator
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Navigation Items
                        NavigationDrawerItem(
                            label = { Text("Terminal Sessions", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Terminal, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.TERMINAL,
                            onClick = {
                                viewModel.navigateTo(AppScreen.TERMINAL)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Built-in Code Editor", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Code, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.EDITOR,
                            onClick = {
                                viewModel.navigateTo(AppScreen.EDITOR)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("File Explorer", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Folder, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.FILES,
                            onClick = {
                                viewModel.navigateTo(AppScreen.FILES)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Git Status & Commits", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Commit, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.GIT,
                            onClick = {
                                viewModel.refreshGitStatus()
                                viewModel.navigateTo(AppScreen.GIT)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("AI Automation Workflows", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.AutoMode, contentDescription = null, tint = theme.aiIndicator) },
                            selected = currentScreen == AppScreen.WORKFLOWS,
                            onClick = {
                                viewModel.navigateTo(AppScreen.WORKFLOWS)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Themes & Appearance (60+)", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Palette, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.THEMES,
                            onClick = {
                                viewModel.navigateTo(AppScreen.THEMES)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Command Snippets", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.BookmarkBorder, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.SNIPPETS,
                            onClick = {
                                viewModel.navigateTo(AppScreen.SNIPPETS)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Plugin Extensions", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Extension, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.PLUGINS,
                            onClick = {
                                viewModel.navigateTo(AppScreen.PLUGINS)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                        NavigationDrawerItem(
                            label = { Text("Settings & AI Config", fontWeight = FontWeight.Medium) },
                            icon = { Icon(Icons.Default.Settings, contentDescription = null, tint = theme.accent) },
                            selected = currentScreen == AppScreen.SETTINGS,
                            onClick = {
                                viewModel.navigateTo(AppScreen.SETTINGS)
                                scope.launch { drawerState.close() }
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        Surface(
                            color = theme.surface,
                            border = BorderStroke(1.dp, theme.border),
                            modifier = Modifier.fillMaxWidth().statusBarsPadding()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    IconButton(
                                        onClick = { scope.launch { drawerState.open() } },
                                        modifier = Modifier.size(36.dp).testTag("navigation_drawer_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu",
                                            tint = theme.foreground
                                        )
                                    }
                                    Text(
                                        text = "ROHAN AI TERMINAL",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 13.sp,
                                        color = theme.foreground,
                                        letterSpacing = 0.5.sp
                                    )
                                }

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    // Command Palette button (Ctrl+K)
                                    IconButton(
                                        onClick = { viewModel.setCommandPaletteOpen(true) },
                                        modifier = Modifier.size(36.dp).testTag("open_command_palette_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = "Command Palette",
                                            tint = theme.accent
                                        )
                                    }

                                    // Quick Theme button
                                    IconButton(
                                        onClick = { viewModel.navigateTo(AppScreen.THEMES) },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Palette,
                                            contentDescription = "Themes",
                                            tint = theme.foreground.copy(alpha = 0.8f)
                                        )
                                    }

                                    // AI Assistant Button
                                    IconButton(
                                        onClick = { viewModel.setAiChatOpen(true) },
                                        modifier = Modifier.size(36.dp).testTag("top_ai_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AutoAwesome,
                                            contentDescription = "AI Assistant",
                                            tint = theme.aiIndicator
                                        )
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize().navigationBarsPadding()
                ) { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        when (currentScreen) {
                            AppScreen.TERMINAL -> TerminalScreen(viewModel = viewModel, theme = theme)
                            AppScreen.EDITOR -> EditorScreen(viewModel = viewModel, theme = theme)
                            AppScreen.FILES -> FileExplorerScreen(viewModel = viewModel, theme = theme)
                            AppScreen.GIT -> GitScreen(viewModel = viewModel, theme = theme)
                            AppScreen.WORKFLOWS -> WorkflowsScreen(viewModel = viewModel, theme = theme)
                            AppScreen.THEMES -> ThemeCatalogScreen(viewModel = viewModel, activeTheme = theme)
                            AppScreen.SETTINGS -> SettingsScreen(viewModel = viewModel, theme = theme)
                            AppScreen.SNIPPETS -> SnippetsScreen(viewModel = viewModel, theme = theme)
                            AppScreen.PLUGINS -> PluginsScreen(viewModel = viewModel, theme = theme)
                            AppScreen.HISTORY -> TerminalScreen(viewModel = viewModel, theme = theme)
                        }

                        // AI Chat Bottom Sheet
                        if (isAiChatOpen) {
                            AiChatSheet(
                                viewModel = viewModel,
                                theme = theme,
                                onDismiss = { viewModel.setAiChatOpen(false) }
                            )
                        }

                        // AI Context Transparency Inspection Dialog
                        if (isAiInspectionOpen) {
                            AiInspectionDialog(
                                payload = activeContextPayload,
                                theme = theme,
                                onDismiss = { viewModel.setAiInspectionOpen(false) }
                            )
                        }

                        // Destructive Command Guard Confirmation Dialog
                        if (isDestructiveOpen) {
                            DestructiveCommandDialog(
                                proposal = aiProposal,
                                theme = theme,
                                onConfirm = { viewModel.confirmDestructiveExecution() },
                                onDismiss = { viewModel.dismissDestructiveExecution() }
                            )
                        }

                        // Command Palette Modal Dialog
                        if (isCommandPaletteOpen) {
                            CommandPaletteDialog(
                                viewModel = viewModel,
                                theme = theme,
                                onDismiss = { viewModel.setCommandPaletteOpen(false) }
                            )
                        }
                    }
                }
            }
        }
    }
}
