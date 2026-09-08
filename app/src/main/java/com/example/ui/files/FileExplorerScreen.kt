package com.example.ui.files

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
import com.example.editor.FileItem
import com.example.theme.model.TerminalTheme
import com.example.ui.AppScreen
import com.example.ui.MainViewModel
import java.io.File

@Composable
fun FileExplorerScreen(
    viewModel: MainViewModel,
    theme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    val currentDir by viewModel.currentDirectory.collectAsState()
    val files by viewModel.directoryFiles.collectAsState()
    val showHidden by viewModel.showHiddenFiles.collectAsState()

    var showNewFileDialog by remember { mutableStateOf(false) }
    var newFileName by remember { mutableStateOf("") }
    var isCreatingFolder by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(theme.background)
    ) {
        // --- 1. Header & Path Bar ---
        Surface(
            color = theme.surface,
            border = BorderStroke(1.dp, theme.border),
            modifier = Modifier.fillMaxWidth()
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
                        IconButton(
                            onClick = { viewModel.navigateTo(AppScreen.TERMINAL) },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = theme.foreground)
                        }
                        Text(
                            text = "FILE EXPLORER",
                            fontWeight = FontWeight.Bold,
                            color = theme.foreground,
                            fontSize = 14.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // New File button
                        IconButton(
                            onClick = {
                                isCreatingFolder = false
                                newFileName = ""
                                showNewFileDialog = true
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.NoteAdd, contentDescription = "New File", tint = theme.accent)
                        }

                        // New Folder button
                        IconButton(
                            onClick = {
                                isCreatingFolder = true
                                newFileName = ""
                                showNewFileDialog = true
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.CreateNewFolder, contentDescription = "New Folder", tint = theme.accent)
                        }

                        // Toggle Hidden files
                        IconButton(
                            onClick = { viewModel.toggleHiddenFiles() },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = if (showHidden) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Toggle Hidden",
                                tint = if (showHidden) theme.accent else theme.foreground.copy(alpha = 0.5f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Breadcrumbs & Parent Directory Button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (currentDir.parentFile != null) {
                        Surface(
                            color = theme.background,
                            shape = RoundedCornerShape(4.dp),
                            border = BorderStroke(1.dp, theme.border),
                            modifier = Modifier.clickable {
                                viewModel.openDirectory(currentDir.parentFile!!)
                            }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            ) {
                                Icon(Icons.Default.ArrowUpward, contentDescription = "Up", tint = theme.accent, modifier = Modifier.size(14.dp))
                                Text("UP", color = theme.accent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = currentDir.absolutePath.replace("/data/user/0/com.example/files/home", "~"),
                        fontFamily = FontFamily.Monospace,
                        color = theme.foreground.copy(alpha = 0.75f),
                        fontSize = 11.sp,
                        maxLines = 1
                    )
                }
            }
        }

        // --- 2. Files List ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(files) { item ->
                FileListItem(
                    item = item,
                    theme = theme,
                    onOpen = {
                        if (item.isDirectory) {
                            viewModel.openDirectory(item.file)
                        } else {
                            viewModel.openFileInEditor(item.file)
                        }
                    },
                    onOpenInTerminal = {
                        val path = if (item.isDirectory) item.file.absolutePath else item.file.parentFile?.absolutePath ?: ""
                        viewModel.sessionManager.currentSession?.executeCommand("cd \"$path\"")
                        viewModel.navigateTo(AppScreen.TERMINAL)
                    },
                    onDelete = {
                        viewModel.fileManager.deleteFileOrDirectory(item.file)
                        viewModel.refreshDirectoryFiles()
                    }
                )
            }
        }
    }

    // Dialog for creating a new file or directory
    if (showNewFileDialog) {
        AlertDialog(
            onDismissRequest = { showNewFileDialog = false },
            containerColor = theme.surface,
            title = {
                Text(
                    text = if (isCreatingFolder) "Create Directory" else "Create New File",
                    color = theme.foreground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            },
            text = {
                androidx.compose.foundation.text.BasicTextField(
                    value = newFileName,
                    onValueChange = { newFileName = it },
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = theme.foreground,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.background, RoundedCornerShape(4.dp))
                        .padding(8.dp)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newFileName.isNotBlank()) {
                            if (isCreatingFolder) {
                                viewModel.fileManager.createDirectory(currentDir, newFileName.trim())
                            } else {
                                val f = viewModel.fileManager.createFile(currentDir, newFileName.trim())
                                viewModel.openFileInEditor(f)
                            }
                            viewModel.refreshDirectoryFiles()
                        }
                        showNewFileDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = theme.accent)
                ) {
                    Text("Create", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showNewFileDialog = false }) {
                    Text("Cancel", color = theme.foreground.copy(alpha = 0.6f))
                }
            }
        )
    }
}

@Composable
fun FileListItem(
    item: FileItem,
    theme: TerminalTheme,
    onOpen: () -> Unit,
    onOpenInTerminal: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        color = theme.surface,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, theme.border),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = if (item.isDirectory) Icons.Default.Folder else Icons.Default.Description,
                    contentDescription = null,
                    tint = if (item.isDirectory) theme.accent else theme.foreground.copy(alpha = 0.8f),
                    modifier = Modifier.size(20.dp)
                )

                Column {
                    Text(
                        text = item.name,
                        color = theme.foreground,
                        fontSize = 13.sp,
                        fontWeight = if (item.isDirectory) FontWeight.Bold else FontWeight.Normal
                    )
                    Text(
                        text = "${item.permissions} • ${if (item.isDirectory) "dir" else "${item.sizeBytes} B"}",
                        fontFamily = FontFamily.Monospace,
                        color = theme.foreground.copy(alpha = 0.5f),
                        fontSize = 10.sp
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(onClick = onOpenInTerminal, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.Terminal,
                        contentDescription = "Terminal Here",
                        tint = theme.aiIndicator,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF5252),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
