package com.example.ui.ai

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.model.AiContextLevel
import com.example.ai.model.GemmaToTResult
import com.example.ai.model.QwenReviewResult
import com.example.theme.model.TerminalTheme
import com.example.ui.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiChatSheet(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    onDismiss: () -> Unit
) {
    val messages by viewModel.aiMessages.collectAsState()
    val aiConfig by viewModel.aiConfig.collectAsState()
    val gemmaToT by viewModel.gemmaToTResult.collectAsState()
    val qwenReview by viewModel.qwenReviewResult.collectAsState()
    val contextPayload by viewModel.activeAiContextPayload.collectAsState()

    var inputMessage by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = theme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle(color = theme.border) },
        modifier = Modifier.testTag("ai_chat_modal_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .padding(horizontal = 16.dp)
        ) {
            // Header: Model Badge & Context Level & Inspection
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = "AI",
                        tint = theme.aiIndicator,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "ROHAN AI ASSISTANT",
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 14.sp
                    )
                }

                // Context Level Pill & Token estimator
                Surface(
                    color = theme.background,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, theme.border),
                    modifier = Modifier.clickable { viewModel.setAiInspectionOpen(true) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "${aiConfig.ollamaModel} • ~${contextPayload?.estimatedTokens ?: 80} tok",
                            color = theme.accent,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Inspect",
                            tint = theme.accent,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quick Prompt Suggestions Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                QuickPromptChip("Explain Error", theme) {
                    viewModel.explainLastError()
                }
                QuickPromptChip("Gemma ToT", theme) {
                    viewModel.runGemmaToT("Evaluate system architecture trade-offs for Android terminal emulator")
                }
                QuickPromptChip("Qwen Review", theme) {
                    val cwd = viewModel.sessionManager.currentSession?.workingDirectoryFlow?.value ?: ""
                    viewModel.runQwenReview(cwd)
                }
                QuickPromptChip("Find Large Files", theme) {
                    viewModel.sendAiMessage("How do I find files larger than 50MB in current directory?")
                }
                QuickPromptChip("Git Commit Helper", theme) {
                    viewModel.sendAiMessage("Suggest a git commit command based on my recent changes.")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Message Stream + ToT & Qwen Cards
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Gemma Tree-of-Thoughts Card if present
                gemmaToT?.let { tot ->
                    item {
                        GemmaToTCard(result = tot, theme = theme)
                    }
                }

                // Qwen Review Card if present
                qwenReview?.let { review ->
                    item {
                        QwenReviewCard(
                            result = review,
                            theme = theme,
                            onApplyPatch = {
                                review.suggestedFix?.let { patch ->
                                    viewModel.sessionManager.currentSession?.executeCommand("echo '$patch' > patch.diff")
                                }
                            }
                        )
                    }
                }

                if (messages.isEmpty() && gemmaToT == null && qwenReview == null) {
                    item {
                        Surface(
                            color = theme.background,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = "Ready to assist your terminal workflow.",
                                    fontWeight = FontWeight.Bold,
                                    color = theme.foreground,
                                    fontSize = 13.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Ask questions, diagnose failed commands, trigger Gemma Tree-of-Thoughts planning, or invoke Qwen code reviews.",
                                    color = theme.foreground.copy(alpha = 0.7f),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                items(messages) { (sender, text) ->
                    AiMessageBubble(
                        sender = sender,
                        text = text,
                        theme = theme,
                        onExecuteCommand = { cmd ->
                            viewModel.sessionManager.currentSession?.executeCommand(cmd)
                            onDismiss()
                        }
                    )
                }
            }

            // Input Bar
            Surface(
                color = theme.background,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, theme.border),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    androidx.compose.foundation.text.BasicTextField(
                        value = inputMessage,
                        onValueChange = { inputMessage = it },
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = theme.foreground,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("ai_chat_input_field")
                    )

                    IconButton(
                        onClick = {
                            val msg = inputMessage
                            inputMessage = ""
                            viewModel.sendAiMessage(msg)
                        },
                        modifier = Modifier.size(36.dp).testTag("ai_chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = theme.aiIndicator,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuickPromptChip(label: String, theme: TerminalTheme, onClick: () -> Unit) {
    Surface(
        color = theme.background,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = theme.foreground.copy(alpha = 0.85f),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun AiMessageBubble(
    sender: String,
    text: String,
    theme: TerminalTheme,
    onExecuteCommand: (String) -> Unit
) {
    val isUser = sender == "user"
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Text(
            text = if (isUser) "You" else "AI Assistant",
            fontSize = 10.sp,
            color = if (isUser) theme.accent else theme.aiIndicator,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 2.dp)
        )

        Surface(
            color = if (isUser) theme.accent.copy(alpha = 0.2f) else theme.background,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, if (isUser) theme.accent.copy(alpha = 0.4f) else theme.border),
            modifier = Modifier.widthIn(max = 340.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = text,
                    color = theme.foreground,
                    fontSize = 12.sp,
                    fontFamily = if (isUser) FontFamily.Default else FontFamily.Monospace
                )

                // If text contains a command proposal line, render Run button
                val commandMatch = Regex("""`([^`]+)`""").find(text)
                if (!isUser && commandMatch != null && !commandMatch.value.contains("\n")) {
                    val cmd = commandMatch.groupValues[1]
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = { onExecuteCommand(cmd) },
                        colors = ButtonDefaults.buttonColors(containerColor = theme.aiIndicator),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Run: $cmd",
                            fontSize = 10.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GemmaToTCard(result: GemmaToTResult, theme: TerminalTheme) {
    Card(
        colors = CardDefaults.cardColors(containerColor = theme.background),
        border = BorderStroke(1.dp, Color(0xFF10B981)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.AccountTree,
                    contentDescription = null,
                    tint = Color(0xFF10B981),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Gemma Tree-of-Thoughts [Mode: ${result.mode.uppercase()}]",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10B981),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Problem: ${result.problem}",
                color = theme.foreground,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))
            result.candidateApproaches.forEach { app ->
                Surface(
                    color = theme.surface,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, theme.border),
                    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = app.title,
                            fontWeight = FontWeight.Bold,
                            color = theme.accent,
                            fontSize = 11.sp
                        )
                        Text(
                            text = app.description,
                            color = theme.foreground.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                        Row(modifier = Modifier.padding(top = 4.dp)) {
                            Text(
                                text = "+ Pros: ${app.advantages.joinToString(", ")}",
                                color = Color(0xFF34D399),
                                fontSize = 10.sp
                            )
                        }
                        Row {
                            Text(
                                text = "- Cons: ${app.disadvantages.joinToString(", ")}",
                                color = Color(0xFFF87171),
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Selected: ${result.selectedApproach}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF10B981),
                fontSize = 11.sp
            )
            Text(
                text = result.finalRecommendation,
                color = theme.foreground.copy(alpha = 0.85f),
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun QwenReviewCard(
    result: QwenReviewResult,
    theme: TerminalTheme,
    onApplyPatch: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = theme.background),
        border = BorderStroke(1.dp, Color(0xFFC084FC)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Code,
                    contentDescription = null,
                    tint = Color(0xFFC084FC),
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Qwen Code Review & Diff Proposal",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFC084FC),
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Target: ${result.target}",
                fontFamily = FontFamily.Monospace,
                color = theme.foreground,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(6.dp))
            result.findings.forEach { finding ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (finding.severity == "ERROR") Color.Red else Color.Yellow)
                    )
                    Text(
                        text = "[Line ${finding.line ?: 0}] ${finding.message}",
                        color = theme.foreground.copy(alpha = 0.85f),
                        fontSize = 11.sp
                    )
                }
            }

            result.proposedDiff?.let { diff ->
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = Color(0xFF101014),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = diff,
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFF86EFAC),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onApplyPatch,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC084FC)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Apply Patch", fontSize = 11.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
