package com.example.ui.ai

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.model.AiCommandProposal
import com.example.ai.model.AiContextPayload
import com.example.theme.model.TerminalTheme

@Composable
fun AiInspectionDialog(
    payload: AiContextPayload?,
    theme: TerminalTheme,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = theme.surface,
        title = {
            Text(
                text = "AI Context Transparency Inspection",
                color = theme.foreground,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Exact context payload submitted to LLM backend:",
                    color = theme.foreground.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )

                Surface(
                    color = theme.background,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, theme.border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(text = "Target Model: ${payload?.targetModel ?: "qwen2.5"}", color = theme.accent, fontSize = 11.sp)
                        Text(text = "Estimated Tokens: ~${payload?.estimatedTokens ?: 0}", color = theme.aiIndicator, fontSize = 11.sp)
                        Text(text = "Context Level: ${payload?.level ?: "TERMINAL"}", color = theme.foreground, fontSize = 11.sp)
                        Text(text = "Working Directory: ${payload?.workingDirectory ?: ""}", color = theme.foreground, fontSize = 11.sp)
                    }
                }

                Surface(
                    color = Color(0xFF0D0D12),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = payload?.formattedContextText ?: "No payload data.",
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFE2E8F0),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close", color = theme.accent, fontWeight = FontWeight.Bold)
            }
        }
    )
}

@Composable
fun DestructiveCommandDialog(
    proposal: AiCommandProposal?,
    theme: TerminalTheme,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E0A0A),
        icon = {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Danger",
                tint = Color(0xFFFF5252),
                modifier = Modifier.size(32.dp)
            )
        },
        title = {
            Text(
                text = "Potentially Destructive Command",
                color = Color(0xFFFF5252),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = proposal?.safetyWarning ?: "This command performs irreversible modifications to files or system state.",
                    color = Color(0xFFFFCDD2),
                    fontSize = 12.sp
                )

                Surface(
                    color = Color(0xFF0A0303),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(1.dp, Color(0xFFFF5252)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = proposal?.command ?: "",
                        fontFamily = FontFamily.Monospace,
                        color = Color(0xFFFF8A80),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Text(
                    text = "Are you sure you want to execute this command in your terminal session?",
                    color = Color(0xFFFFCDD2),
                    fontSize = 12.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.testTag("confirm_destructive_button")
            ) {
                Text("Proceed & Execute", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Color(0xFFB0BEC5))
            }
        }
    )
}
