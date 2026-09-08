package com.example.ui.plugins

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.plugin.TerminalPlugin
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun PluginsScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    var plugins by remember { mutableStateOf(viewModel.pluginManager.getInstalledPlugins()) }

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
                    text = "PLUGIN EXTENSIONS",
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
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(plugins) { plugin ->
                PluginCard(
                    plugin = plugin,
                    theme = theme,
                    onToggle = {
                        viewModel.togglePlugin(plugin.id)
                        plugins = viewModel.pluginManager.getInstalledPlugins()
                    }
                )
            }
        }
    }
}

@Composable
fun PluginCard(
    plugin: TerminalPlugin,
    theme: TerminalTheme,
    onToggle: () -> Unit
) {
    Surface(
        color = theme.surface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier.fillMaxWidth().testTag("plugin_card_${plugin.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = plugin.name,
                            fontWeight = FontWeight.Bold,
                            color = theme.foreground,
                            fontSize = 13.sp
                        )
                        Surface(
                            color = theme.accent.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "v${plugin.version}",
                                color = theme.accent,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(
                        text = "by ${plugin.author} • ${plugin.category}",
                        fontSize = 10.sp,
                        color = theme.foreground.copy(alpha = 0.5f)
                    )
                }

                Switch(
                    checked = plugin.isEnabled,
                    onCheckedChange = { onToggle() },
                    colors = SwitchDefaults.colors(checkedThumbColor = theme.accent)
                )
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = plugin.description,
                color = theme.foreground.copy(alpha = 0.8f),
                fontSize = 11.sp
            )

            if (plugin.providedCommands.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    plugin.providedCommands.forEach { cmd ->
                        Surface(
                            color = theme.background,
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, theme.border)
                        ) {
                            Text(
                                text = cmd,
                                fontFamily = FontFamily.Monospace,
                                color = theme.accent,
                                fontSize = 9.sp,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
