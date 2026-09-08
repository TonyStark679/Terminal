package com.example.ui.terminal

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.terminal.engine.TerminalLine
import com.example.theme.model.TerminalTheme
import com.example.ui.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun TerminalScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val sessions by viewModel.sessionManager.sessions.collectAsState()
    val activeIndex by viewModel.sessionManager.activeSessionIndex.collectAsState()
    val currentSession = viewModel.sessionManager.currentSession
    val fontSize by viewModel.terminalFontSize.collectAsState()
    val isCtrlPressed by viewModel.isCtrlPressed.collectAsState()
    val isAltPressed by viewModel.isAltPressed.collectAsState()
    val aiProposal by viewModel.aiCommandProposal.collectAsState()

    var inputCommand by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val lines by currentSession?.lines?.collectAsState() ?: remember { mutableStateOf(emptyList()) }
    val isRunning by currentSession?.isRunning?.collectAsState() ?: remember { mutableStateOf(false) }
    val workingDir by currentSession?.workingDirectoryFlow?.collectAsState() ?: remember { mutableStateOf("~") }

    // Auto scroll to bottom when new lines arrive
    LaunchedEffect(lines.size) {
        if (lines.isNotEmpty()) {
            listState.animateScrollToItem(lines.size - 1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // --- 1. Terminal Session Tab Bar ---
        ScrollableTabRow(
            selectedTabIndex = activeIndex.coerceIn(0, (sessions.size - 1).coerceAtLeast(0)),
            containerColor = theme.surface,
            contentColor = theme.foreground,
            edgePadding = 8.dp,
            divider = {},
            modifier = Modifier.fillMaxWidth().height(44.dp)
        ) {
            sessions.forEachIndexed { index, session ->
                val isSelected = index == activeIndex
                val tabTitle by session.titleFlow.collectAsState()
                Tab(
                    selected = isSelected,
                    onClick = { viewModel.sessionManager.selectSession(index) },
                    modifier = Modifier.testTag("tab_session_$index")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isSelected) theme.accent else theme.border)
                        )
                        Text(
                            text = tabTitle,
                            color = if (isSelected) theme.foreground else theme.foreground.copy(alpha = 0.6f),
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            maxLines = 1
                        )
                        if (sessions.size > 1) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close tab",
                                tint = theme.foreground.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .size(14.dp)
                                    .clickable { viewModel.sessionManager.killSession(index) }
                            )
                        }
                    }
                }
            }

            // New Session Button
            IconButton(
                onClick = { viewModel.sessionManager.createNewSession() },
                modifier = Modifier.size(36.dp).testTag("new_session_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Terminal Session",
                    tint = theme.accent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // --- 2. Session Info Bar ---
        Surface(
            color = theme.surface.copy(alpha = 0.85f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Status indicator + Working Directory
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(if (isRunning) Color(0xFF00E676) else Color(0xFFFF5252))
                    )
                    Text(
                        text = workingDir.replace("/data/user/0/com.example/files/home", "~"),
                        color = theme.foreground.copy(alpha = 0.8f),
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // AI Context Pill & Action Icons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Token inspection pill
                    Surface(
                        color = theme.aiIndicator.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, theme.aiIndicator.copy(alpha = 0.4f)),
                        modifier = Modifier.clickable { viewModel.setAiInspectionOpen(true) }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI Inspection",
                                tint = theme.aiIndicator,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "AI Context",
                                color = theme.aiIndicator,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Clear terminal output
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Clear",
                        tint = theme.foreground.copy(alpha = 0.7f),
                        modifier = Modifier
                            .size(16.dp)
                            .clickable { currentSession?.clear() }
                    )
                }
            }
        }

        // --- 3. Terminal Viewport (LazyColumn with ANSI rendering) ---
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            SelectionContainer {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("terminal_output_list")
                ) {
                    items(lines) { line ->
                        TerminalLineItem(line = line, theme = theme, fontSize = fontSize)
                    }
                }
            }

            // Inline AI Error / Proposal Card (floating overlay if error occurred)
            androidx.compose.animation.AnimatedVisibility(
                visible = aiProposal != null,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                aiProposal?.let { proposal ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (proposal.isDestructive) Color(0xFF330B0B) else theme.surface
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (proposal.isDestructive) Color(0xFFFF5252) else theme.aiIndicator
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().testTag("ai_proposal_card")
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (proposal.isDestructive) Icons.Default.Warning else Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = if (proposal.isDestructive) Color(0xFFFF5252) else theme.aiIndicator,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = if (proposal.isDestructive) "Caution: High-Risk Command" else "AI Suggested Command",
                                        fontWeight = FontWeight.Bold,
                                        color = if (proposal.isDestructive) Color(0xFFFF5252) else theme.aiIndicator,
                                        fontSize = 12.sp
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss",
                                    tint = theme.foreground.copy(alpha = 0.5f),
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clickable { viewModel.explainLastError() /* clears or minimizes */ }
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = proposal.explanation.take(200),
                                color = theme.foreground.copy(alpha = 0.85f),
                                fontSize = 12.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                color = theme.background,
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = proposal.command,
                                    fontFamily = FontFamily.Monospace,
                                    color = theme.accent,
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { viewModel.executeProposedCommand(proposal) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (proposal.isDestructive) Color(0xFFFF5252) else theme.aiIndicator
                                    ),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.testTag("execute_ai_proposal_button")
                                ) {
                                    Text(
                                        text = if (proposal.isDestructive) "Verify & Run" else "Run Command",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // --- 4. Extra Key Row (ESC, TAB, CTRL, ALT, /, -, ↑, ↓, etc.) ---
        Surface(
            color = theme.surface,
            modifier = Modifier.fillMaxWidth().height(42.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Key: ESC
                KeyPill(label = "ESC", theme = theme, onClick = { viewModel.executeKeyAction("ESC") })

                // Key: TAB
                KeyPill(label = "TAB", theme = theme, onClick = { viewModel.executeKeyAction("TAB") })

                // Key: CTRL toggle
                KeyPill(
                    label = "CTRL",
                    isActive = isCtrlPressed,
                    theme = theme,
                    onClick = { viewModel.toggleCtrl() }
                )

                // Key: ALT toggle
                KeyPill(
                    label = "ALT",
                    isActive = isAltPressed,
                    theme = theme,
                    onClick = { viewModel.toggleAlt() }
                )

                // Navigation Arrows
                KeyPill(label = "↑", theme = theme, onClick = { viewModel.executeKeyAction("↑") })
                KeyPill(label = "↓", theme = theme, onClick = { viewModel.executeKeyAction("↓") })
                KeyPill(label = "←", theme = theme, onClick = { viewModel.executeKeyAction("←") })
                KeyPill(label = "→", theme = theme, onClick = { viewModel.executeKeyAction("→") })

                // Punctuation & Terminal Shortcuts
                KeyPill(label = "/", theme = theme, onClick = { viewModel.executeKeyAction("/") })
                KeyPill(label = "-", theme = theme, onClick = { viewModel.executeKeyAction("-") })
                KeyPill(label = "|", theme = theme, onClick = { viewModel.executeKeyAction("|") })
                KeyPill(label = "~", theme = theme, onClick = { viewModel.executeKeyAction("~") })
                KeyPill(label = "^C", theme = theme, onClick = { currentSession?.sendCtrlC() })
                KeyPill(label = "^D", theme = theme, onClick = { currentSession?.sendCtrlD() })
            }
        }

        // --- 5. Terminal Command Input Bar ---
        Surface(
            color = theme.surface,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp, vertical = 6.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, theme.border)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$ ",
                    color = theme.accent,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                BasicTextField(
                    value = inputCommand,
                    onValueChange = { inputCommand = it },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = theme.foreground,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                        .testTag("terminal_command_input")
                )

                // Quick Send Button
                IconButton(
                    onClick = {
                        val cmd = inputCommand
                        inputCommand = ""
                        currentSession?.executeCommand(cmd)
                    },
                    modifier = Modifier.size(36.dp).testTag("send_command_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Execute Command",
                        tint = theme.accent,
                        modifier = Modifier.size(18.dp)
                    )
                }

                // AI Assist Floating/Direct Button
                IconButton(
                    onClick = { viewModel.setAiChatOpen(true) },
                    modifier = Modifier.size(36.dp).testTag("open_ai_chat_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI Assistant",
                        tint = theme.aiIndicator,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun KeyPill(
    label: String,
    theme: TerminalTheme,
    isActive: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        color = if (isActive) theme.accent else theme.background,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, if (isActive) theme.accent else theme.border),
        modifier = Modifier
            .defaultMinSize(minWidth = 40.dp, minHeight = 32.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = label,
                color = if (isActive) Color.Black else theme.foreground,
                fontSize = 11.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
            )
        }
    }
}

@Composable
fun TerminalLineItem(
    line: TerminalLine,
    theme: TerminalTheme,
    fontSize: Int
) {
    val annotatedString = buildAnnotatedString {
        line.spans.forEach { span ->
            val fg = span.foreground ?: if (line.isError) Color(0xFFFF5252) else theme.foreground
            val bg = span.background ?: Color.Transparent

            withStyle(
                style = SpanStyle(
                    color = fg,
                    background = bg,
                    fontWeight = span.fontWeight,
                    fontStyle = span.fontStyle,
                    textDecoration = span.textDecoration
                )
            ) {
                append(span.text)
            }
        }
    }

    Text(
        text = annotatedString,
        fontFamily = FontFamily.Monospace,
        fontSize = fontSize.sp,
        lineHeight = (fontSize + 5).sp,
        modifier = Modifier.fillMaxWidth()
    )
}
