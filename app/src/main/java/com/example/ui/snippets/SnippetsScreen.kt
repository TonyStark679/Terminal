package com.example.ui.snippets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.data.database.Snippet
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun SnippetsScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val snippets by viewModel.snippetsFlow.collectAsState()

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
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.navigateTo(AppScreen.TERMINAL) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = theme.foreground)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SNIPPETS & RECIPES",
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground,
                    fontSize = 14.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(snippets) { snip ->
                SnippetCard(
                    snippet = snip,
                    theme = theme,
                    onExecute = {
                        viewModel.sessionManager.currentSession?.executeCommand(snip.command)
                        viewModel.navigateTo(AppScreen.TERMINAL)
                    }
                )
            }
        }
    }
}

@Composable
fun SnippetCard(
    snippet: Snippet,
    theme: TerminalTheme,
    onExecute: () -> Unit
) {
    Surface(
        color = theme.surface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        color = theme.accent.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = snippet.category,
                            color = theme.accent,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = snippet.title,
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 13.sp
                    )
                }
                snippet.description?.let {
                    Text(
                        text = it,
                        color = theme.foreground.copy(alpha = 0.65f),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = theme.background,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = snippet.command,
                        fontFamily = FontFamily.Monospace,
                        color = theme.foreground,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                    )
                }
            }

            IconButton(onClick = onExecute, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Run",
                    tint = theme.accent
                )
            }
        }
    }
}
