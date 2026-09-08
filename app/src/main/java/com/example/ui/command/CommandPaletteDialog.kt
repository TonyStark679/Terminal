package com.example.ui.command

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.window.Dialog
import com.example.command.CommandCategory
import com.example.command.PaletteAction
import com.example.theme.model.TerminalTheme
import com.example.ui.MainViewModel

@Composable
fun CommandPaletteDialog(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val allActions = remember { viewModel.getCommandPaletteActions() }

    val filteredActions = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            allActions
        } else {
            val q = searchQuery.lowercase()
            allActions.filter {
                it.title.lowercase().contains(q) ||
                        (it.subtitle?.lowercase()?.contains(q) == true) ||
                        it.category.name.lowercase().contains(q) ||
                        (it.shortcut?.lowercase()?.contains(q) == true)
            }
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            color = theme.surface,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, theme.border),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 520.dp)
                .testTag("command_palette_dialog")
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Search Input Header
                Surface(
                    color = theme.background,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, theme.border),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = theme.accent,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        androidx.compose.foundation.text.BasicTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            textStyle = androidx.compose.ui.text.TextStyle(
                                color = theme.foreground,
                                fontSize = 14.sp
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("command_palette_search_input")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "ACTIONS (${filteredActions.size})",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground.copy(alpha = 0.5f)
                )

                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(filteredActions) { action ->
                        PaletteActionItem(
                            action = action,
                            theme = theme,
                            onClick = {
                                onDismiss()
                                action.onExecute()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaletteActionItem(
    action: PaletteAction,
    theme: TerminalTheme,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
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
                            text = action.category.name,
                            color = theme.accent,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                    Text(
                        text = action.title,
                        color = theme.foreground,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                action.subtitle?.let {
                    Text(
                        text = it,
                        color = theme.foreground.copy(alpha = 0.6f),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(start = 2.dp, top = 2.dp)
                    )
                }
            }

            action.shortcut?.let { shortcut ->
                Surface(
                    color = theme.background,
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, theme.border)
                ) {
                    Text(
                        text = shortcut,
                        fontFamily = FontFamily.Monospace,
                        color = theme.foreground.copy(alpha = 0.8f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
