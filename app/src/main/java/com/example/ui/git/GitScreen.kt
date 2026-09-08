package com.example.ui.git

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.git.GitCommitItem
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun GitScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val status by viewModel.gitStatus.collectAsState()
    val commits by viewModel.gitCommits.collectAsState()

    var aiCommitMsg by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // --- Header ---
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = theme.foreground)
                    }
                    Text(
                        text = "GIT INTEGRATION",
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    color = theme.accent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "BRANCH: ${status.currentBranch}",
                        fontFamily = FontFamily.Monospace,
                        color = theme.accent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = theme.surface),
                    border = BorderStroke(1.dp, theme.border),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "WORKING TREE STATUS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = theme.accent
                        )
                        Spacer(modifier = Modifier.height(6.dp))

                        if (status.modifiedFiles.isEmpty() && status.untrackedFiles.isEmpty()) {
                            Text(
                                text = "Working tree clean. No pending changes.",
                                color = Color(0xFF00E676),
                                fontSize = 12.sp
                            )
                        } else {
                            status.modifiedFiles.forEach { file ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Text("M", color = Color(0xFFFFB74D), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text(file, color = theme.foreground, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                                }
                            }
                            status.untrackedFiles.forEach { file ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                ) {
                                    Text("?", color = Color(0xFF81D4FA), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                    Text(file, color = theme.foreground, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // AI Commit Generator Button
                        Button(
                            onClick = {
                                aiCommitMsg = viewModel.gitManager.generateCommitMessage("fun main() update")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = theme.aiIndicator),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.fillMaxWidth().testTag("generate_ai_commit_button")
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate AI Commit Message", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        if (aiCommitMsg.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                color = theme.background,
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, theme.border),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text("Suggested Commit Message:", fontSize = 10.sp, color = theme.foreground.copy(alpha = 0.6f))
                                    Text(aiCommitMsg, color = theme.accent, fontFamily = FontFamily.Monospace, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Recent Commits Section
            item {
                Text(
                    text = "RECENT COMMITS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground.copy(alpha = 0.6f)
                )
            }

            items(commits) { commit ->
                Surface(
                    color = theme.surface,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, theme.border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = commit.hash,
                                fontFamily = FontFamily.Monospace,
                                color = theme.accent,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Text(
                                text = commit.date,
                                color = theme.foreground.copy(alpha = 0.5f),
                                fontSize = 10.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = commit.message,
                            color = theme.foreground,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "by ${commit.author}",
                            color = theme.foreground.copy(alpha = 0.5f),
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
