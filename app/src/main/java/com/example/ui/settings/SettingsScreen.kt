package com.example.ui.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.ai.model.AiContextLevel
import com.example.ai.model.AiProviderType
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun SettingsScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val aiConfig by viewModel.aiConfig.collectAsState()
    val fontSize by viewModel.terminalFontSize.collectAsState()

    var ollamaUrl by remember(aiConfig.ollamaEndpoint) { mutableStateOf(aiConfig.ollamaEndpoint) }
    var qwenModel by remember(aiConfig.ollamaModel) { mutableStateOf(aiConfig.ollamaModel) }
    var gemmaModel by remember(aiConfig.gemmaModel) { mutableStateOf(aiConfig.gemmaModel) }

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
                    text = "SETTINGS & PREFERENCES",
                    fontWeight = FontWeight.Bold,
                    color = theme.foreground,
                    fontSize = 14.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // --- 1. Terminal Font Size ---
            Surface(
                color = theme.surface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, theme.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "TERMINAL FONT SIZE: ${fontSize}sp",
                        fontWeight = FontWeight.Bold,
                        color = theme.accent,
                        fontSize = 12.sp
                    )
                    Slider(
                        value = fontSize.toFloat(),
                        onValueChange = { viewModel.setFontSize(it.toInt()) },
                        valueRange = 10f..22f,
                        steps = 11,
                        colors = SliderDefaults.colors(thumbColor = theme.accent, activeTrackColor = theme.accent),
                        modifier = Modifier.testTag("font_size_slider")
                    )
                }
            }

            // --- 2. AI Provider Configuration ---
            Surface(
                color = theme.surface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, theme.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "AI BACKEND & PROVIDER",
                        fontWeight = FontWeight.Bold,
                        color = theme.aiIndicator,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AiProviderType.values().forEach { type ->
                            val isSelected = aiConfig.provider == type
                            Surface(
                                color = if (isSelected) theme.aiIndicator else theme.background,
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(1.dp, if (isSelected) theme.aiIndicator else theme.border),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.updateAiConfig(aiConfig.copy(provider = type)) }
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 6.dp)) {
                                    Text(
                                        text = type.name.replace("_", " "),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else theme.foreground
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Ollama Endpoint & Models
                    Text("Ollama Endpoint URL", fontSize = 11.sp, color = theme.foreground.copy(alpha = 0.7f))
                    OutlinedTextField(
                        value = ollamaUrl,
                        onValueChange = {
                            ollamaUrl = it
                            viewModel.updateAiConfig(aiConfig.copy(ollamaEndpoint = it))
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = theme.foreground, fontFamily = FontFamily.Monospace),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).testTag("ollama_url_input")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Qwen Model Identifier", fontSize = 11.sp, color = theme.foreground.copy(alpha = 0.7f))
                    OutlinedTextField(
                        value = qwenModel,
                        onValueChange = {
                            qwenModel = it
                            viewModel.updateAiConfig(aiConfig.copy(ollamaModel = it))
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = theme.foreground, fontFamily = FontFamily.Monospace),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Gemma Model Identifier (Tree-of-Thoughts)", fontSize = 11.sp, color = theme.foreground.copy(alpha = 0.7f))
                    OutlinedTextField(
                        value = gemmaModel,
                        onValueChange = {
                            gemmaModel = it
                            viewModel.updateAiConfig(aiConfig.copy(gemmaModel = it))
                        },
                        textStyle = androidx.compose.ui.text.TextStyle(fontSize = 12.sp, color = theme.foreground, fontFamily = FontFamily.Monospace),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    )
                }
            }

            // --- 3. AI Context Level ---
            Surface(
                color = theme.surface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, theme.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "AI CONTEXT TRANSPARENCY LEVEL",
                        fontWeight = FontWeight.Bold,
                        color = theme.accent,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AiContextLevel.values().take(4).forEach { lvl ->
                            val isSelected = aiConfig.contextLevel == lvl
                            Surface(
                                color = if (isSelected) theme.accent else theme.background,
                                shape = RoundedCornerShape(4.dp),
                                border = BorderStroke(1.dp, theme.border),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.updateAiConfig(aiConfig.copy(contextLevel = lvl)) }
                            ) {
                                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(vertical = 6.dp)) {
                                    Text(
                                        text = lvl.name,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) Color.Black else theme.foreground
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // --- 4. Environment Information ---
            Surface(
                color = theme.surface,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, theme.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SYSTEM ENVIRONMENT",
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground.copy(alpha = 0.6f),
                        fontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Shell: Android Bionic /system/bin/sh", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = theme.foreground)
                    Text("Home: ${viewModel.shellEnvironment.homeDir.absolutePath}", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = theme.foreground)
                    Text("Bin: ${viewModel.shellEnvironment.binDir.absolutePath}", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = theme.foreground)
                    Text("Termux Bridge: ${if (viewModel.shellEnvironment.hasTermuxBridge) "Connected" else "Standalone Native"}", fontFamily = FontFamily.Monospace, fontSize = 11.sp, color = theme.accent)
                }
            }
        }
    }
}
