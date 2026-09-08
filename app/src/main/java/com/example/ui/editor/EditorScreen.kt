package com.example.ui.editor

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun EditorScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val document by viewModel.editorDocument.collectAsState()

    var textState by remember(document?.file?.absolutePath) {
        mutableStateOf(document?.content ?: "")
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // --- 1. Editor Top Action Bar ---
        Surface(
            color = theme.surface,
            border = BorderStroke(1.dp, theme.border),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { viewModel.navigateTo(AppScreen.TERMINAL) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = theme.foreground
                        )
                    }

                    Text(
                        text = document?.file?.name ?: "untitled.txt",
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 14.sp
                    )

                    if (document?.isDirty == true) {
                        Surface(
                            color = Color(0xFFFFB74D),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "MODIFIED",
                                fontSize = 9.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Surface(
                        color = theme.accent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = document?.language?.uppercase() ?: "TEXT",
                            fontSize = 9.sp,
                            color = theme.accent,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Quick Save Button
                    IconButton(
                        onClick = { viewModel.saveEditorFile() },
                        modifier = Modifier.size(36.dp).testTag("save_editor_file_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            tint = if (document?.isDirty == true) Color(0xFF00E676) else theme.foreground.copy(alpha = 0.6f)
                        )
                    }

                    // AI Quick Assist Button
                    IconButton(
                        onClick = {
                            val path = document?.file?.absolutePath ?: ""
                            viewModel.runQwenReview(path)
                        },
                        modifier = Modifier.size(36.dp).testTag("ai_editor_assist_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = "AI Review",
                            tint = theme.aiIndicator
                        )
                    }
                }
            }
        }

        // --- 2. Editor Quick AI Actions Bar ---
        Surface(
            color = theme.surface.copy(alpha = 0.7f),
            modifier = Modifier.fillMaxWidth().height(36.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                EditorAiChip("Explain Code", theme) {
                    viewModel.sendAiMessage("Explain the following file (${document?.file?.name}):\n```\n$textState\n```")
                    viewModel.setAiChatOpen(true)
                }
                EditorAiChip("Generate Unit Test", theme) {
                    viewModel.sendAiMessage("Generate comprehensive unit tests for this code (${document?.file?.name}):\n```\n$textState\n```")
                    viewModel.setAiChatOpen(true)
                }
                EditorAiChip("Refactor & Optimize", theme) {
                    viewModel.sendAiMessage("Refactor and optimize this code for Android Kotlin best practices:\n```\n$textState\n```")
                    viewModel.setAiChatOpen(true)
                }
            }
        }

        // --- 3. Text Editor Content with Line Numbers ---
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            // Line numbers column
            val lineCount = textState.count { it == '\n' } + 1
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(36.dp)
            ) {
                for (i in 1..lineCount) {
                    Text(
                        text = "$i",
                        color = theme.foreground.copy(alpha = 0.35f),
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 18.sp
                    )
                }
            }

            // Editable Text Area
            BasicTextField(
                value = textState,
                onValueChange = {
                    textState = it
                    viewModel.updateEditorContent(it)
                },
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = theme.foreground,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .testTag("code_editor_text_field")
            )
        }
    }
}

@Composable
fun EditorAiChip(label: String, theme: TerminalTheme, onClick: () -> Unit) {
    Surface(
        color = theme.background,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = label,
            color = theme.foreground.copy(alpha = 0.8f),
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
