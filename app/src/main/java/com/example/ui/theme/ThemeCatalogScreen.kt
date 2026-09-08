package com.example.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import com.example.theme.model.TerminalTheme
import com.example.theme.model.ThemeCategory
import com.example.theme.model.ThemeCatalog
import com.example.ui.AppScreen
import com.example.ui.MainViewModel

@Composable
fun ThemeCatalogScreen(
    viewModel: MainViewModel,
    activeTheme: TerminalTheme,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<ThemeCategory?>(null) }

    val themes = remember(selectedCategory) {
        if (selectedCategory == null) {
            ThemeCatalog.allThemes
        } else {
            ThemeCatalog.allThemes.filter { it.category == selectedCategory }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(activeTheme.background)
    ) {
        // --- Header ---
        Surface(
            color = activeTheme.surface,
            border = BorderStroke(1.dp, activeTheme.border),
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = activeTheme.foreground)
                    }
                    Text(
                        text = "THEMES (${ThemeCatalog.allThemes.size})",
                        fontWeight = FontWeight.Bold,
                        color = activeTheme.foreground,
                        fontSize = 14.sp
                    )
                }

                Surface(
                    color = activeTheme.accent.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = activeTheme.name,
                        color = activeTheme.accent,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // --- Category Filters ---
        ScrollableTabRow(
            selectedTabIndex = if (selectedCategory == null) 0 else selectedCategory!!.ordinal + 1,
            containerColor = activeTheme.surface,
            contentColor = activeTheme.foreground,
            edgePadding = 8.dp,
            divider = {},
            modifier = Modifier.fillMaxWidth().height(40.dp)
        ) {
            Tab(
                selected = selectedCategory == null,
                onClick = { selectedCategory = null }
            ) {
                Text(
                    text = "All",
                    fontSize = 11.sp,
                    fontWeight = if (selectedCategory == null) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedCategory == null) activeTheme.accent else activeTheme.foreground.copy(alpha = 0.6f),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
            ThemeCategory.values().forEach { cat ->
                val isSelected = selectedCategory == cat
                Tab(
                    selected = isSelected,
                    onClick = { selectedCategory = cat }
                ) {
                    Text(
                        text = cat.name,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) activeTheme.accent else activeTheme.foreground.copy(alpha = 0.6f),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // --- Theme Grid / List ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(themes) { itemTheme ->
                val isCurrent = itemTheme.id == activeTheme.id
                ThemePreviewCard(
                    theme = itemTheme,
                    isCurrent = isCurrent,
                    onSelect = { viewModel.setTheme(itemTheme) }
                )
            }
        }
    }
}

@Composable
fun ThemePreviewCard(
    theme: TerminalTheme,
    isCurrent: Boolean,
    onSelect: () -> Unit
) {
    Surface(
        color = theme.background,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(if (isCurrent) 2.dp else 1.dp, if (isCurrent) theme.accent else theme.border),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect)
            .testTag("theme_card_${theme.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = theme.name,
                        fontWeight = FontWeight.Bold,
                        color = theme.foreground,
                        fontSize = 13.sp
                    )
                    Surface(
                        color = theme.surface,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = theme.category.name,
                            color = theme.foreground.copy(alpha = 0.6f),
                            fontSize = 9.sp,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }

                if (isCurrent) {
                    Surface(
                        color = theme.accent,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(12.dp))
                            Text("ACTIVE", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // ANSI Palette color dots preview
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ColorDot(theme.ansi.red)
                ColorDot(theme.ansi.green)
                ColorDot(theme.ansi.yellow)
                ColorDot(theme.ansi.blue)
                ColorDot(theme.ansi.magenta)
                ColorDot(theme.ansi.cyan)
                ColorDot(theme.ansi.brightRed)
                ColorDot(theme.ansi.brightGreen)
                ColorDot(theme.ansi.brightBlue)
                ColorDot(theme.ansi.brightCyan)
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Mini terminal mockup line
            Text(
                text = "rohan@terminal:~$ ls -la # ${theme.id}",
                fontFamily = FontFamily.Monospace,
                color = theme.ansi.green,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ColorDot(color: Color) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
    )
}
