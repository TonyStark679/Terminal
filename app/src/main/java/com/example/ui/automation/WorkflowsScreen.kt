package com.example.ui.automation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
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
import com.example.automation.WorkflowExecutionLog
import com.example.data.database.AutomationWorkflow
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun WorkflowsScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val workflows by viewModel.workflowsFlow.collectAsState()
    val executionLogs by viewModel.workflowEngine.executionLogs.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // Header
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
                        text = "AI AUTOMATION ENGINE",
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    color = theme.aiIndicator.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "ACTIVE",
                        color = theme.aiIndicator,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Text(
                    text = "CONFIGURED WORKFLOWS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground.copy(alpha = 0.6f)
                )
            }

            items(workflows) { wf ->
                WorkflowCard(
                    workflow = wf,
                    theme = theme,
                    onToggle = { viewModel.toggleWorkflow(wf) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "EXECUTION HISTORY (${executionLogs.size})",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground.copy(alpha = 0.6f)
                )
            }

            if (executionLogs.isEmpty()) {
                item {
                    Surface(
                        color = theme.surface,
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "No workflows triggered yet. Run commands with errors or patterns to see real-time workflow logs.",
                            color = theme.foreground.copy(alpha = 0.6f),
                            fontSize = 11.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }

            items(executionLogs) { log ->
                ExecutionLogItem(log = log, theme = theme)
            }
        }
    }
}

@Composable
fun WorkflowCard(
    workflow: AutomationWorkflow,
    theme: TerminalTheme,
    onToggle: () -> Unit
) {
    Surface(
        color = theme.surface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.fillMaxWidth().testTag("workflow_item_${workflow.id}")
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = workflow.name,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Surface(
                        color = theme.background,
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, theme.border)
                    ) {
                        Text(
                            text = "Trigger: ${workflow.triggerType}",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = theme.accent,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                    Surface(
                        color = theme.background,
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, theme.border)
                    ) {
                        Text(
                            text = "Action: ${workflow.actionType}",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = theme.aiIndicator,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Switch(
                checked = workflow.isEnabled,
                onCheckedChange = { onToggle() },
                colors = SwitchDefaults.colors(checkedThumbColor = theme.accent)
            )
        }
    }
}

@Composable
fun ExecutionLogItem(log: WorkflowExecutionLog, theme: TerminalTheme) {
    Surface(
        color = theme.surface,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = log.workflowName,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground,
                    fontSize = 11.sp
                )
                Surface(
                    color = if (log.status == "SUCCESS") Color(0xFF00E676).copy(alpha = 0.2f) else Color.Red.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = log.status,
                        color = if (log.status == "SUCCESS") Color(0xFF00E676) else Color(0xFFFF5252),
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = log.output,
                fontFamily = FontFamily.Monospace,
                color = theme.foreground.copy(alpha = 0.75f),
                fontSize = 10.sp
            )
        }
    }
}
