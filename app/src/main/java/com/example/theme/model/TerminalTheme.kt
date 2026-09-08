package com.example.theme.model

import androidx.compose.ui.graphics.Color

data class AnsiColors(
    val black: Color,
    val red: Color,
    val green: Color,
    val yellow: Color,
    val blue: Color,
    val magenta: Color,
    val cyan: Color,
    val white: Color,
    val brightBlack: Color,
    val brightRed: Color,
    val brightGreen: Color,
    val brightYellow: Color,
    val brightBlue: Color,
    val brightMagenta: Color,
    val brightCyan: Color,
    val brightWhite: Color
)

enum class ThemeCategory {
    Dark,
    Light,
    Retro,
    Minimal,
    Neon,
    Nature,
    Developer,
    HighContrast
}

data class TerminalTheme(
    val id: String,
    val name: String,
    val category: ThemeCategory,
    val background: Color,
    val foreground: Color,
    val cursor: Color,
    val selection: Color,
    val accent: Color,
    val surface: Color,
    val border: Color,
    val aiIndicator: Color,
    val ansi: AnsiColors
)
