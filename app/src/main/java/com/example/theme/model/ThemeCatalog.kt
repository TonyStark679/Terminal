package com.example.theme.model

import androidx.compose.ui.graphics.Color

object ThemeCatalog {

    val allThemes: List<TerminalTheme> by lazy {
        listOf(
            // 1. Tokyo Night (Dark / Developer)
            TerminalTheme(
                id = "tokyo_night",
                name = "Tokyo Night",
                category = ThemeCategory.Dark,
                background = Color(0xFF1A1B26),
                foreground = Color(0xFFA9B1D6),
                cursor = Color(0xFFC0CAF5),
                selection = Color(0xFF283457),
                accent = Color(0xFF7AA2F7),
                surface = Color(0xFF24283B),
                border = Color(0xFF414868),
                aiIndicator = Color(0xFFBB9AF7),
                ansi = AnsiColors(
                    black = Color(0xFF32344A), red = Color(0xFFF7768E), green = Color(0xFF9ECE6A), yellow = Color(0xFFE0AF68),
                    blue = Color(0xFF7AA2F7), magenta = Color(0xFFAD8EE6), cyan = Color(0xFF449DAB), white = Color(0xFF787C99),
                    brightBlack = Color(0xFF444B6A), brightRed = Color(0xFFFF7A93), brightGreen = Color(0xFFB9F27C), brightYellow = Color(0xFFFF9E3B),
                    brightBlue = Color(0xFF7DA6FF), brightMagenta = Color(0xFFBB9AF7), brightCyan = Color(0xFF0DB9D7), brightWhite = Color(0xFFACB0D0)
                )
            ),
            // 2. Tokyo Night Storm
            TerminalTheme(
                id = "tokyo_night_storm",
                name = "Tokyo Night Storm",
                category = ThemeCategory.Dark,
                background = Color(0xFF24283B),
                foreground = Color(0xFFC0CAF5),
                cursor = Color(0xFF7AA2F7),
                selection = Color(0xFF2E3C64),
                accent = Color(0xFF7DCFFF),
                surface = Color(0xFF1F2335),
                border = Color(0xFF3B4261),
                aiIndicator = Color(0xFF2AC3DE),
                ansi = AnsiColors(
                    black = Color(0xFF32344A), red = Color(0xFFF7768E), green = Color(0xFF9ECE6A), yellow = Color(0xFFE0AF68),
                    blue = Color(0xFF7AA2F7), magenta = Color(0xFFBB9AF7), cyan = Color(0xFF7DCFFF), white = Color(0xFFA9B1D6),
                    brightBlack = Color(0xFF444B6A), brightRed = Color(0xFFFF7A93), brightGreen = Color(0xFFB9F27C), brightYellow = Color(0xFFFF9E3B),
                    brightBlue = Color(0xFF7DA6FF), brightMagenta = Color(0xFFC0CAF5), brightCyan = Color(0xFF0DB9D7), brightWhite = Color(0xFFC0CAF5)
                )
            ),
            // 3. Catppuccin Mocha
            TerminalTheme(
                id = "catppuccin_mocha",
                name = "Catppuccin Mocha",
                category = ThemeCategory.Dark,
                background = Color(0xFF1E1E2E),
                foreground = Color(0xFFCDD6F4),
                cursor = Color(0xFFF5E0DC),
                selection = Color(0xFF45475A),
                accent = Color(0xFF89B4FA),
                surface = Color(0xFF181825),
                border = Color(0xFF313244),
                aiIndicator = Color(0xFFCBA6F7),
                ansi = AnsiColors(
                    black = Color(0xFF45475A), red = Color(0xFFF38BA8), green = Color(0xFFA6E3A1), yellow = Color(0xFFF9E2AF),
                    blue = Color(0xFF89B4FA), magenta = Color(0xFFF5C2E7), cyan = Color(0xFF94E2D5), white = Color(0xFFBAC2DE),
                    brightBlack = Color(0xFF585B70), brightRed = Color(0xFFF38BA8), brightGreen = Color(0xFFA6E3A1), brightYellow = Color(0xFFF9E2AF),
                    brightBlue = Color(0xFF89B4FA), brightMagenta = Color(0xFFF5C2E7), brightCyan = Color(0xFF94E2D5), brightWhite = Color(0xFFA6ADC8)
                )
            ),
            // 4. Catppuccin Macchiato
            TerminalTheme(
                id = "catppuccin_macchiato",
                name = "Catppuccin Macchiato",
                category = ThemeCategory.Dark,
                background = Color(0xFF24273A),
                foreground = Color(0xFFCAD3F5),
                cursor = Color(0xFFF4DBD6),
                selection = Color(0xFF494D64),
                accent = Color(0xFF8AADF4),
                surface = Color(0xFF1E2030),
                border = Color(0xFF363A4F),
                aiIndicator = Color(0xFFC6A0F6),
                ansi = AnsiColors(
                    black = Color(0xFF494D64), red = Color(0xFFED8796), green = Color(0xFFA6DA95), yellow = Color(0xFFEED49F),
                    blue = Color(0xFF8AADF4), magenta = Color(0xFFF5BDE6), cyan = Color(0xFF8BD5CA), white = Color(0xFFB8C0E0),
                    brightBlack = Color(0xFF5B6078), brightRed = Color(0xFFED8796), brightGreen = Color(0xFFA6DA95), brightYellow = Color(0xFFEED49F),
                    brightBlue = Color(0xFF8AADF4), brightMagenta = Color(0xFFF5BDE6), brightCyan = Color(0xFF8BD5CA), brightWhite = Color(0xFFA5ADCB)
                )
            ),
            // 5. Catppuccin Frappé
            TerminalTheme(
                id = "catppuccin_frappe",
                name = "Catppuccin Frappé",
                category = ThemeCategory.Dark,
                background = Color(0xFF303446),
                foreground = Color(0xFFC6D0F5),
                cursor = Color(0xFFF2D5CF),
                selection = Color(0xFF51576D),
                accent = Color(0xFF8CAAEE),
                surface = Color(0xFF292C3C),
                border = Color(0xFF414559),
                aiIndicator = Color(0xFFCA9EE6),
                ansi = AnsiColors(
                    black = Color(0xFF51576D), red = Color(0xFFE78284), green = Color(0xFFA6D189), yellow = Color(0xFFE5C890),
                    blue = Color(0xFF8CAAEE), magenta = Color(0xFFF4B8E4), cyan = Color(0xFF81C8BE), white = Color(0xFFB5BFE2),
                    brightBlack = Color(0xFF626880), brightRed = Color(0xFFE78284), brightGreen = Color(0xFFA6D189), brightYellow = Color(0xFFE5C890),
                    brightBlue = Color(0xFF8CAAEE), brightMagenta = Color(0xFFF4B8E4), brightCyan = Color(0xFF81C8BE), brightWhite = Color(0xFFA5ADCE)
                )
            ),
            // 6. Catppuccin Latte
            TerminalTheme(
                id = "catppuccin_latte",
                name = "Catppuccin Latte",
                category = ThemeCategory.Light,
                background = Color(0xFFEFF1F5),
                foreground = Color(0xFF4C4F69),
                cursor = Color(0xFFDC8A78),
                selection = Color(0xFFCCD0DA),
                accent = Color(0xFF1E66F5),
                surface = Color(0xFFE6E9EF),
                border = Color(0xFFBCC0CC),
                aiIndicator = Color(0xFF8839EF),
                ansi = AnsiColors(
                    black = Color(0xFF5C5F77), red = Color(0xFFD20F39), green = Color(0xFF40A02B), yellow = Color(0xFFDF8E1D),
                    blue = Color(0xFF1E66F5), magenta = Color(0xFFEA76CB), cyan = Color(0xFF179299), white = Color(0xFFACB0BE),
                    brightBlack = Color(0xFF6C6F85), brightRed = Color(0xFFD20F39), brightGreen = Color(0xFF40A02B), brightYellow = Color(0xFFDF8E1D),
                    brightBlue = Color(0xFF1E66F5), brightMagenta = Color(0xFFEA76CB), brightCyan = Color(0xFF179299), brightWhite = Color(0xFFBCC0CC)
                )
            ),
            // 7. Dracula
            TerminalTheme(
                id = "dracula",
                name = "Dracula",
                category = ThemeCategory.Dark,
                background = Color(0xFF282A36),
                foreground = Color(0xFFF8F8F2),
                cursor = Color(0xFF50FA7B),
                selection = Color(0xFF44475A),
                accent = Color(0xFFBD93F9),
                surface = Color(0xFF21222C),
                border = Color(0xFF6272A4),
                aiIndicator = Color(0xFFFF79C6),
                ansi = AnsiColors(
                    black = Color(0xFF21222C), red = Color(0xFFFF5555), green = Color(0xFF50FA7B), yellow = Color(0xFFF1FA8C),
                    blue = Color(0xFFBD93F9), magenta = Color(0xFFFF79C6), cyan = Color(0xFF8BE9FD), white = Color(0xFFF8F8F2),
                    brightBlack = Color(0xFF6272A4), brightRed = Color(0xFFFF6E6E), brightGreen = Color(0xFF69FF94), brightYellow = Color(0xFFFFFFA5),
                    brightBlue = Color(0xFFD6ACFF), brightMagenta = Color(0xFFFF92DF), brightCyan = Color(0xFFA4FFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 8. Gruvbox Dark
            TerminalTheme(
                id = "gruvbox_dark",
                name = "Gruvbox Dark",
                category = ThemeCategory.Retro,
                background = Color(0xFF282828),
                foreground = Color(0xFFEBDBB2),
                cursor = Color(0xFFFE8019),
                selection = Color(0xFF504945),
                accent = Color(0xFFFABD2F),
                surface = Color(0xFF1D2021),
                border = Color(0xFF665C54),
                aiIndicator = Color(0xFFB8BB26),
                ansi = AnsiColors(
                    black = Color(0xFF282828), red = Color(0xFFCC241D), green = Color(0xFF98971A), yellow = Color(0xFFD79921),
                    blue = Color(0xFF458588), magenta = Color(0xFFB16286), cyan = Color(0xFF689D6A), white = Color(0xFFA89984),
                    brightBlack = Color(0xFF928374), brightRed = Color(0xFFFB4934), brightGreen = Color(0xFFB8BB26), brightYellow = Color(0xFFFABD2F),
                    brightBlue = Color(0xFF83A598), brightMagenta = Color(0xFFD3869B), brightCyan = Color(0xFF8EC07C), brightWhite = Color(0xFFEBDBB2)
                )
            ),
            // 9. Gruvbox Light
            TerminalTheme(
                id = "gruvbox_light",
                name = "Gruvbox Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFBF1C7),
                foreground = Color(0xFF3C3836),
                cursor = Color(0xFFAF3A03),
                selection = Color(0xFFEBDBB2),
                accent = Color(0xFFB57614),
                surface = Color(0xFFF2E5BC),
                border = Color(0xFFD5C4A1),
                aiIndicator = Color(0xFF79740E),
                ansi = AnsiColors(
                    black = Color(0xFF7C6F64), red = Color(0xFF9D0006), green = Color(0xFF79740E), yellow = Color(0xFFB57614),
                    blue = Color(0xFF076678), magenta = Color(0xFF8F3F71), cyan = Color(0xFF427B58), white = Color(0xFF3C3836),
                    brightBlack = Color(0xFF928374), brightRed = Color(0xFFCC241D), brightGreen = Color(0xFF98971A), brightYellow = Color(0xFFD79921),
                    brightBlue = Color(0xFF458588), brightMagenta = Color(0xFFB16286), brightCyan = Color(0xFF689D6A), brightWhite = Color(0xFF282828)
                )
            ),
            // 10. Nord
            TerminalTheme(
                id = "nord",
                name = "Nord",
                category = ThemeCategory.Developer,
                background = Color(0xFF2E3440),
                foreground = Color(0xFFD8DEE9),
                cursor = Color(0xFF88C0D0),
                selection = Color(0xFF434C5E),
                accent = Color(0xFF88C0D0),
                surface = Color(0xFF242933),
                border = Color(0xFF4C566A),
                aiIndicator = Color(0xFF81A1C1),
                ansi = AnsiColors(
                    black = Color(0xFF3B4252), red = Color(0xFFBF616A), green = Color(0xFFA3BE8C), yellow = Color(0xFFEBCB8B),
                    blue = Color(0xFF81A1C1), magenta = Color(0xFFB48EAD), cyan = Color(0xFF88C0D0), white = Color(0xFFE5E9F0),
                    brightBlack = Color(0xFF4C566A), brightRed = Color(0xFFD08770), brightGreen = Color(0xFFA3BE8C), brightYellow = Color(0xFFEBCB8B),
                    brightBlue = Color(0xFF5E81AC), brightMagenta = Color(0xFFB48EAD), brightCyan = Color(0xFF8FBCBB), brightWhite = Color(0xFFECEFF4)
                )
            ),
            // 11. One Dark
            TerminalTheme(
                id = "one_dark",
                name = "One Dark",
                category = ThemeCategory.Developer,
                background = Color(0xFF282C34),
                foreground = Color(0xFFABB2BF),
                cursor = Color(0xFF528BFF),
                selection = Color(0xFF3E4451),
                accent = Color(0xFF61AFEF),
                surface = Color(0xFF21252B),
                border = Color(0xFF4B5263),
                aiIndicator = Color(0xFF98C379),
                ansi = AnsiColors(
                    black = Color(0xFF3F4451), red = Color(0xFFE06C75), green = Color(0xFF98C379), yellow = Color(0xFFE5C07B),
                    blue = Color(0xFF61AFEF), magenta = Color(0xFFC678DD), cyan = Color(0xFF56B6C2), white = Color(0xFFABB2BF),
                    brightBlack = Color(0xFF4F5666), brightRed = Color(0xFFE06C75), brightGreen = Color(0xFF98C379), brightYellow = Color(0xFFE5C07B),
                    brightBlue = Color(0xFF61AFEF), brightMagenta = Color(0xFFC678DD), brightCyan = Color(0xFF56B6C2), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 12. One Light
            TerminalTheme(
                id = "one_light",
                name = "One Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFAFAFA),
                foreground = Color(0xFF383A42),
                cursor = Color(0xFF526FFF),
                selection = Color(0xFFE5E5E6),
                accent = Color(0xFF4078F2),
                surface = Color(0xFFF0F0F0),
                border = Color(0xFFD0D0D0),
                aiIndicator = Color(0xFF50A14F),
                ansi = AnsiColors(
                    black = Color(0xFF383A42), red = Color(0xFFE45649), green = Color(0xFF50A14F), yellow = Color(0xFFC18401),
                    blue = Color(0xFF4078F2), magenta = Color(0xFFA626A4), cyan = Color(0xFF0184BC), white = Color(0xFFA0A1A7),
                    brightBlack = Color(0xFF4F525D), brightRed = Color(0xFFDF4037), brightGreen = Color(0xFF408D3F), brightYellow = Color(0xFFB07300),
                    brightBlue = Color(0xFF3067E2), brightMagenta = Color(0xFF951493), brightCyan = Color(0xFF0074A6), brightWhite = Color(0xFF23252B)
                )
            ),
            // 13. Solarized Dark
            TerminalTheme(
                id = "solarized_dark",
                name = "Solarized Dark",
                category = ThemeCategory.Developer,
                background = Color(0xFF002B36),
                foreground = Color(0xFF839496),
                cursor = Color(0xFF268BD2),
                selection = Color(0xFF073642),
                accent = Color(0xFF2AA198),
                surface = Color(0xFF00212B),
                border = Color(0xFF586E75),
                aiIndicator = Color(0xFF859900),
                ansi = AnsiColors(
                    black = Color(0xFF073642), red = Color(0xFFDC322F), green = Color(0xFF859900), yellow = Color(0xFFB58900),
                    blue = Color(0xFF268BD2), magenta = Color(0xFFD33682), cyan = Color(0xFF2AA198), white = Color(0xFFEEE8D5),
                    brightBlack = Color(0xFF002B36), brightRed = Color(0xFFCB4B16), brightGreen = Color(0xFF586E75), brightYellow = Color(0xFF657B83),
                    brightBlue = Color(0xFF839496), brightMagenta = Color(0xFF6C71C4), brightCyan = Color(0xFF93A1A1), brightWhite = Color(0xFFFDF6E3)
                )
            ),
            // 14. Solarized Light
            TerminalTheme(
                id = "solarized_light",
                name = "Solarized Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFDF6E3),
                foreground = Color(0xFF657B83),
                cursor = Color(0xFF268BD2),
                selection = Color(0xFFEEE8D5),
                accent = Color(0xFF268BD2),
                surface = Color(0xFFF5EFDC),
                border = Color(0xFFD33682),
                aiIndicator = Color(0xFF2AA198),
                ansi = AnsiColors(
                    black = Color(0xFF073642), red = Color(0xFFDC322F), green = Color(0xFF859900), yellow = Color(0xFFB58900),
                    blue = Color(0xFF268BD2), magenta = Color(0xFFD33682), cyan = Color(0xFF2AA198), white = Color(0xFFEEE8D5),
                    brightBlack = Color(0xFF002B36), brightRed = Color(0xFFCB4B16), brightGreen = Color(0xFF586E75), brightYellow = Color(0xFF657B83),
                    brightBlue = Color(0xFF839496), brightMagenta = Color(0xFF6C71C4), brightCyan = Color(0xFF93A1A1), brightWhite = Color(0xFFFDF6E3)
                )
            ),
            // 15. Monokai
            TerminalTheme(
                id = "monokai",
                name = "Monokai",
                category = ThemeCategory.Developer,
                background = Color(0xFF272822),
                foreground = Color(0xFFF8F8F2),
                cursor = Color(0xFFA6E22E),
                selection = Color(0xFF49483E),
                accent = Color(0xFFF92672),
                surface = Color(0xFF1E1F1C),
                border = Color(0xFF75715E),
                aiIndicator = Color(0xFF66D9EF),
                ansi = AnsiColors(
                    black = Color(0xFF272822), red = Color(0xFFF92672), green = Color(0xFFA6E22E), yellow = Color(0xFFF4BF75),
                    blue = Color(0xFF66D9EF), magenta = Color(0xFFAE81FF), cyan = Color(0xFFA1EFE4), white = Color(0xFFF8F8F2),
                    brightBlack = Color(0xFF75715E), brightRed = Color(0xFFF92672), brightGreen = Color(0xFFA6E22E), brightYellow = Color(0xFFF4BF75),
                    brightBlue = Color(0xFF66D9EF), brightMagenta = Color(0xFFAE81FF), brightCyan = Color(0xFFA1EFE4), brightWhite = Color(0xFFF9F8F5)
                )
            ),
            // 16. Monokai Pro
            TerminalTheme(
                id = "monokai_pro",
                name = "Monokai Pro",
                category = ThemeCategory.Developer,
                background = Color(0xFF2D2A2E),
                foreground = Color(0xFFFCFCFA),
                cursor = Color(0xFFFFD866),
                selection = Color(0xFF403E41),
                accent = Color(0xFFFF6188),
                surface = Color(0xFF221F22),
                border = Color(0xFF5B595C),
                aiIndicator = Color(0xFFA9DC76),
                ansi = AnsiColors(
                    black = Color(0xFF403E41), red = Color(0xFFFF6188), green = Color(0xFFA9DC76), yellow = Color(0xFFFFD866),
                    blue = Color(0xFFFC9867), magenta = Color(0xFFAB9DF2), cyan = Color(0xFF78DCE8), white = Color(0xFFFCFCFA),
                    brightBlack = Color(0xFF727072), brightRed = Color(0xFFFF6188), brightGreen = Color(0xFFA9DC76), brightYellow = Color(0xFFFFD866),
                    brightBlue = Color(0xFFFC9867), brightMagenta = Color(0xFFAB9DF2), brightCyan = Color(0xFF78DCE8), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 17. Matrix (Retro / Neon)
            TerminalTheme(
                id = "matrix",
                name = "Matrix",
                category = ThemeCategory.Retro,
                background = Color(0xFF030A03),
                foreground = Color(0xFF00FF66),
                cursor = Color(0xFF33FF88),
                selection = Color(0xFF0A3311),
                accent = Color(0xFF00FF44),
                surface = Color(0xFF011401),
                border = Color(0xFF006622),
                aiIndicator = Color(0xFF66FF99),
                ansi = AnsiColors(
                    black = Color(0xFF001100), red = Color(0xFF00AA33), green = Color(0xFF00FF66), yellow = Color(0xFF33FF99),
                    blue = Color(0xFF00CC44), magenta = Color(0xFF22AA55), cyan = Color(0xFF44FF88), white = Color(0xFFAAFFCC),
                    brightBlack = Color(0xFF004411), brightRed = Color(0xFF00EE55), brightGreen = Color(0xFF33FFAA), brightYellow = Color(0xFF66FFBB),
                    brightBlue = Color(0xFF00FF66), brightMagenta = Color(0xFF55FFAA), brightCyan = Color(0xFF88FFDD), brightWhite = Color(0xFFEEFFEE)
                )
            ),
            // 18. Cyberpunk
            TerminalTheme(
                id = "cyberpunk",
                name = "Cyberpunk",
                category = ThemeCategory.Neon,
                background = Color(0xFF0B0E14),
                foreground = Color(0xFF00F0FF),
                cursor = Color(0xFFFF0055),
                selection = Color(0xFF2B1055),
                accent = Color(0xFFFFE600),
                surface = Color(0xFF140D24),
                border = Color(0xFFFF007F),
                aiIndicator = Color(0xFF00FFCC),
                ansi = AnsiColors(
                    black = Color(0xFF101020), red = Color(0xFFFF0055), green = Color(0xFF00FF66), yellow = Color(0xFFFFE600),
                    blue = Color(0xFF00E5FF), magenta = Color(0xFFFF00D4), cyan = Color(0xFF00FFFF), white = Color(0xFFE0F7FA),
                    brightBlack = Color(0xFF302050), brightRed = Color(0xFFFF3377), brightGreen = Color(0xFF33FF88), brightYellow = Color(0xFFFFF033),
                    brightBlue = Color(0xFF33EEFF), brightMagenta = Color(0xFFFF33EE), brightCyan = Color(0xFF66FFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 19. Synthwave
            TerminalTheme(
                id = "synthwave",
                name = "Synthwave 84",
                category = ThemeCategory.Neon,
                background = Color(0xFF262335),
                foreground = Color(0xFFFDFDFD),
                cursor = Color(0xFFFF7EDB),
                selection = Color(0xFF463465),
                accent = Color(0xFFFE4450),
                surface = Color(0xFF1C1A27),
                border = Color(0xFF725AC1),
                aiIndicator = Color(0xFF36F9F6),
                ansi = AnsiColors(
                    black = Color(0xFF2A2139), red = Color(0xFFFE4450), green = Color(0xFF72F1B8), yellow = Color(0xFFFEDE5D),
                    blue = Color(0xFF03EDF9), magenta = Color(0xFFFF7EDB), cyan = Color(0xFF36F9F6), white = Color(0xFFFEFFFF),
                    brightBlack = Color(0xFF614D85), brightRed = Color(0xFFFF6270), brightGreen = Color(0xFF8EFFC8), brightYellow = Color(0xFFFEF57D),
                    brightBlue = Color(0xFF4DF7FF), brightMagenta = Color(0xFFFF99E6), brightCyan = Color(0xFF6DFBF8), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 20. AMOLED (Pure Black Minimal)
            TerminalTheme(
                id = "amoled",
                name = "AMOLED Black",
                category = ThemeCategory.Minimal,
                background = Color(0xFF000000),
                foreground = Color(0xFFE6E6E6),
                cursor = Color(0xFF00FFCC),
                selection = Color(0xFF222222),
                accent = Color(0xFF00FFCC),
                surface = Color(0xFF0A0A0A),
                border = Color(0xFF262626),
                aiIndicator = Color(0xFF7000FF),
                ansi = AnsiColors(
                    black = Color(0xFF1A1A1A), red = Color(0xFFFF4D4D), green = Color(0xFF00E676), yellow = Color(0xFFFFD600),
                    blue = Color(0xFF2979FF), magenta = Color(0xFFE040FB), cyan = Color(0xFF00E5FF), white = Color(0xFFF5F5F5),
                    brightBlack = Color(0xFF4D4D4D), brightRed = Color(0xFFFF6E6E), brightGreen = Color(0xFF69F0AE), brightYellow = Color(0xFFFFEA00),
                    brightBlue = Color(0xFF448AFF), brightMagenta = Color(0xFFEA80FC), brightCyan = Color(0xFF18FFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 21. Midnight
            TerminalTheme(
                id = "midnight",
                name = "Midnight Blue",
                category = ThemeCategory.Dark,
                background = Color(0xFF0A0F1D),
                foreground = Color(0xFFD1D8E0),
                cursor = Color(0xFF4B7BEC),
                selection = Color(0xFF1E293B),
                accent = Color(0xFF3867D6),
                surface = Color(0xFF0F172A),
                border = Color(0xFF334155),
                aiIndicator = Color(0xFF20BF6B),
                ansi = AnsiColors(
                    black = Color(0xFF1E272E), red = Color(0xFFEB3B5A), green = Color(0xFF20BF6B), yellow = Color(0xFFFA8231),
                    blue = Color(0xFF3867D6), magenta = Color(0xFF8854D0), cyan = Color(0xFF0FB9B1), white = Color(0xFFD1D8E0),
                    brightBlack = Color(0xFF4B6584), brightRed = Color(0xFFFC5C65), brightGreen = Color(0xFF26DE81), brightYellow = Color(0xFFFED330),
                    brightBlue = Color(0xFF45AAF2), brightMagenta = Color(0xFFA55EEA), brightCyan = Color(0xFF2BCBBA), brightWhite = Color(0xFFF1F2F6)
                )
            ),
            // 22. Ocean
            TerminalTheme(
                id = "ocean",
                name = "Deep Ocean",
                category = ThemeCategory.Nature,
                background = Color(0xFF0F2027),
                foreground = Color(0xFFE0F2F1),
                cursor = Color(0xFF00B4D8),
                selection = Color(0xFF203A43),
                accent = Color(0xFF0077B6),
                surface = Color(0xFF091418),
                border = Color(0xFF2C5364),
                aiIndicator = Color(0xFF90E0EF),
                ansi = AnsiColors(
                    black = Color(0xFF1B3B4B), red = Color(0xFFE63946), green = Color(0xFF2A9D8F), yellow = Color(0xFFE9C46A),
                    blue = Color(0xFF0077B6), magenta = Color(0xFF7209B7), cyan = Color(0xFF00B4D8), white = Color(0xFFCAF0F8),
                    brightBlack = Color(0xFF2A5266), brightRed = Color(0xFFF2545B), brightGreen = Color(0xFF38B2AC), brightYellow = Color(0xFFF4A261),
                    brightBlue = Color(0xFF0096C7), brightMagenta = Color(0xFF9D4EDD), brightCyan = Color(0xFF48CAE4), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 23. Forest
            TerminalTheme(
                id = "forest",
                name = "Nordic Forest",
                category = ThemeCategory.Nature,
                background = Color(0xFF1B261D),
                foreground = Color(0xFFDDE5B6),
                cursor = Color(0xFFA3B18A),
                selection = Color(0xFF344E41),
                accent = Color(0xFF588157),
                surface = Color(0xFF131C15),
                border = Color(0xFF3A5A40),
                aiIndicator = Color(0xFFADC178),
                ansi = AnsiColors(
                    black = Color(0xFF243328), red = Color(0xFFBC4749), green = Color(0xFF588157), yellow = Color(0xFFDDA15E),
                    blue = Color(0xFF6B9080), magenta = Color(0xFFA67C52), cyan = Color(0xFFA3B18A), white = Color(0xFFDDE5B6),
                    brightBlack = Color(0xFF3A5A40), brightRed = Color(0xFFD62828), brightGreen = Color(0xFF6A994E), brightYellow = Color(0xFFF2BB05),
                    brightBlue = Color(0xFF84A98C), brightMagenta = Color(0xFFBC6C25), brightCyan = Color(0xFFCAD2C5), brightWhite = Color(0xFFF0F3F4)
                )
            ),
            // 24. Sunset
            TerminalTheme(
                id = "sunset",
                name = "Sunset Glow",
                category = ThemeCategory.Nature,
                background = Color(0xFF201322),
                foreground = Color(0xFFFDE2E4),
                cursor = Color(0xFFFF9F1C),
                selection = Color(0xFF4A254B),
                accent = Color(0xFFFF5964),
                surface = Color(0xFF180E1A),
                border = Color(0xFF6A396B),
                aiIndicator = Color(0xFFFFB703),
                ansi = AnsiColors(
                    black = Color(0xFF351C36), red = Color(0xFFFF5964), green = Color(0xFF35A7FF), yellow = Color(0xFFFFB703),
                    blue = Color(0xFF9381FF), magenta = Color(0xFFB5179E), cyan = Color(0xFFFF9F1C), white = Color(0xFFFDE2E4),
                    brightBlack = Color(0xFF522E54), brightRed = Color(0xFFFF7A85), brightGreen = Color(0xFF62B6CB), brightYellow = Color(0xFFFFC72C),
                    brightBlue = Color(0xFFB8B8FF), brightMagenta = Color(0xFFD946EF), brightCyan = Color(0xFFFFB04A), brightWhite = Color(0xFFFFF0F3)
                )
            ),
            // 25. Retro Green
            TerminalTheme(
                id = "retro_green",
                name = "VT100 Green",
                category = ThemeCategory.Retro,
                background = Color(0xFF0F1A12),
                foreground = Color(0xFF33FF33),
                cursor = Color(0xFF33FF33),
                selection = Color(0xFF1E3A24),
                accent = Color(0xFF22DD22),
                surface = Color(0xFF080F0A),
                border = Color(0xFF1A4D26),
                aiIndicator = Color(0xFF66FF66),
                ansi = AnsiColors(
                    black = Color(0xFF142418), red = Color(0xFF229922), green = Color(0xFF33FF33), yellow = Color(0xFF66FF33),
                    blue = Color(0xFF11AA44), magenta = Color(0xFF22BB55), cyan = Color(0xFF44FF77), white = Color(0xFF88FFAA),
                    brightBlack = Color(0xFF204028), brightRed = Color(0xFF44CC44), brightGreen = Color(0xFF55FF55), brightYellow = Color(0xFF88FF55),
                    brightBlue = Color(0xFF33DD66), brightMagenta = Color(0xFF44EE77), brightCyan = Color(0xFF77FFAA), brightWhite = Color(0xFFBBFFCC)
                )
            ),
            // 26. Retro Amber
            TerminalTheme(
                id = "retro_amber",
                name = "Amber CRT",
                category = ThemeCategory.Retro,
                background = Color(0xFF1A1200),
                foreground = Color(0xFFFFB000),
                cursor = Color(0xFFFFCC00),
                selection = Color(0xFF402B00),
                accent = Color(0xFFFF9900),
                surface = Color(0xFF110C00),
                border = Color(0xFF5A3D00),
                aiIndicator = Color(0xFFFFD54F),
                ansi = AnsiColors(
                    black = Color(0xFF2E1F00), red = Color(0xFFE65100), green = Color(0xFFFFB300), yellow = Color(0xFFFFCC00),
                    blue = Color(0xFFFFA000), magenta = Color(0xFFFF8F00), cyan = Color(0xFFFFD54F), white = Color(0xFFFFECB3),
                    brightBlack = Color(0xFF4D3400), brightRed = Color(0xFFFF6D00), brightGreen = Color(0xFFFFC107), brightYellow = Color(0xFFFFE082),
                    brightBlue = Color(0xFFFFB74D), brightMagenta = Color(0xFFFFA726), brightCyan = Color(0xFFFFE57F), brightWhite = Color(0xFFFFF8E1)
                )
            ),
            // 27. Hacker
            TerminalTheme(
                id = "hacker",
                name = "Hacker Midnight",
                category = ThemeCategory.Neon,
                background = Color(0xFF000500),
                foreground = Color(0xFF00FF41),
                cursor = Color(0xFF00FF41),
                selection = Color(0xFF003B00),
                accent = Color(0xFF008F11),
                surface = Color(0xFF001100),
                border = Color(0xFF004400),
                aiIndicator = Color(0xFF39FF14),
                ansi = AnsiColors(
                    black = Color(0xFF001F00), red = Color(0xFF008F11), green = Color(0xFF00FF41), yellow = Color(0xFF00DD22),
                    blue = Color(0xFF00AA11), magenta = Color(0xFF00BB22), cyan = Color(0xFF00EE33), white = Color(0xFFAAFFB5),
                    brightBlack = Color(0xFF003800), brightRed = Color(0xFF00B018), brightGreen = Color(0xFF39FF14), brightYellow = Color(0xFF55FF33),
                    brightBlue = Color(0xFF11CC22), brightMagenta = Color(0xFF22DD44), brightCyan = Color(0xFF44FF66), brightWhite = Color(0xFFD4FFDA)
                )
            ),
            // 28. Blood Moon
            TerminalTheme(
                id = "blood_moon",
                name = "Blood Moon",
                category = ThemeCategory.Dark,
                background = Color(0xFF140707),
                foreground = Color(0xFFFFB3B3),
                cursor = Color(0xFFFF2A2A),
                selection = Color(0xFF3B0F0F),
                accent = Color(0xFFE50914),
                surface = Color(0xFF1E0B0B),
                border = Color(0xFF5A1616),
                aiIndicator = Color(0xFFFF5252),
                ansi = AnsiColors(
                    black = Color(0xFF2A0E0E), red = Color(0xFFFF1E27), green = Color(0xFFFFA07A), yellow = Color(0xFFFF8566),
                    blue = Color(0xFFE63946), magenta = Color(0xFFD00000), cyan = Color(0xFFFF7070), white = Color(0xFFFFD6D6),
                    brightBlack = Color(0xFF471717), brightRed = Color(0xFFFF4D55), brightGreen = Color(0xFFFFB899), brightYellow = Color(0xFFFFA488),
                    brightBlue = Color(0xFFFF5E6C), brightMagenta = Color(0xFFF21A1A), brightCyan = Color(0xFFFF8C8C), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 29. Ice
            TerminalTheme(
                id = "ice",
                name = "Arctic Ice",
                category = ThemeCategory.Minimal,
                background = Color(0xFF0D1B2A),
                foreground = Color(0xFFE0E1DD),
                cursor = Color(0xFF70D6FF),
                selection = Color(0xFF1B263B),
                accent = Color(0xFF00B4D8),
                surface = Color(0xFF081018),
                border = Color(0xFF415A77),
                aiIndicator = Color(0xFF90E0EF),
                ansi = AnsiColors(
                    black = Color(0xFF1B263B), red = Color(0xFFFF758F), green = Color(0xFF52B788), yellow = Color(0xFFFFD166),
                    blue = Color(0xFF0077B6), magenta = Color(0xFFB5179E), cyan = Color(0xFF70D6FF), white = Color(0xFFE0E1DD),
                    brightBlack = Color(0xFF415A77), brightRed = Color(0xFFFF97B7), brightGreen = Color(0xFF74C69D), brightYellow = Color(0xFFFFE099),
                    brightBlue = Color(0xFF0096C7), brightMagenta = Color(0xFFD946EF), brightCyan = Color(0xFFADE8F4), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 30. Purple Rain
            TerminalTheme(
                id = "purple_rain",
                name = "Purple Rain",
                category = ThemeCategory.Dark,
                background = Color(0xFF160F29),
                foreground = Color(0xFFE2D9F3),
                cursor = Color(0xFFB79CED),
                selection = Color(0xFF36205D),
                accent = Color(0xFF9A7AA0),
                surface = Color(0xFF1F153B),
                border = Color(0xFF4B2E83),
                aiIndicator = Color(0xFFD8B4E2),
                ansi = AnsiColors(
                    black = Color(0xFF291B48), red = Color(0xFFFF5C8A), green = Color(0xFF7BDFF2), yellow = Color(0xFFFEE440),
                    blue = Color(0xFF9B5DE5), magenta = Color(0xFFF15BB5), cyan = Color(0xFF00BBF9), white = Color(0xFFE2D9F3),
                    brightBlack = Color(0xFF432C7A), brightRed = Color(0xFFFF7DA7), brightGreen = Color(0xFF98E6F5), brightYellow = Color(0xFFFFF07A),
                    brightBlue = Color(0xFFB07EF0), brightMagenta = Color(0xFFF47EC6), brightCyan = Color(0xFF38CDFF), brightWhite = Color(0xFFF6F3FC)
                )
            ),
            // 31. Neon
            TerminalTheme(
                id = "neon",
                name = "High Voltage Neon",
                category = ThemeCategory.Neon,
                background = Color(0xFF05050D),
                foreground = Color(0xFFF0F0FF),
                cursor = Color(0xFF39FF14),
                selection = Color(0xFF1B1B3A),
                accent = Color(0xFFFF007F),
                surface = Color(0xFF0D0D1A),
                border = Color(0xFF2E2E66),
                aiIndicator = Color(0xFF00F5D4),
                ansi = AnsiColors(
                    black = Color(0xFF15152A), red = Color(0xFFFF0055), green = Color(0xFF39FF14), yellow = Color(0xFFFFEE00),
                    blue = Color(0xFF00BFFF), magenta = Color(0xFFFF007F), cyan = Color(0xFF00F5D4), white = Color(0xFFF0F0FF),
                    brightBlack = Color(0xFF333366), brightRed = Color(0xFFFF3377), brightGreen = Color(0xFF66FF44), brightYellow = Color(0xFFFFF244),
                    brightBlue = Color(0xFF33CCFF), brightMagenta = Color(0xFFFF3399), brightCyan = Color(0xFF33F7DD), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 32. Terminal Classic
            TerminalTheme(
                id = "terminal_classic",
                name = "Classic Gray",
                category = ThemeCategory.Minimal,
                background = Color(0xFF121212),
                foreground = Color(0xFFD4D4D4),
                cursor = Color(0xFFFFFFFF),
                selection = Color(0xFF264F78),
                accent = Color(0xFF0E639C),
                surface = Color(0xFF1E1E1E),
                border = Color(0xFF333333),
                aiIndicator = Color(0xFF4EC9B0),
                ansi = AnsiColors(
                    black = Color(0xFF000000), red = Color(0xFFCD3131), green = Color(0xFF0DBC79), yellow = Color(0xFFE5E510),
                    blue = Color(0xFF2472C8), magenta = Color(0xFFBC3FBC), cyan = Color(0xFF11A8CD), white = Color(0xFFE5E5E5),
                    brightBlack = Color(0xFF666666), brightRed = Color(0xFFF14C4C), brightGreen = Color(0xFF23D18B), brightYellow = Color(0xFFF5F543),
                    brightBlue = Color(0xFF3B8EEA), brightMagenta = Color(0xFFD670D6), brightCyan = Color(0xFF29B8DB), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 33. DOS
            TerminalTheme(
                id = "dos",
                name = "MS-DOS Prompt",
                category = ThemeCategory.Retro,
                background = Color(0xFF000000),
                foreground = Color(0xFFAAAAAA),
                cursor = Color(0xFFFFFFFF),
                selection = Color(0xFF0000AA),
                accent = Color(0xFF00AAAA),
                surface = Color(0xFF050505),
                border = Color(0xFF555555),
                aiIndicator = Color(0xFF55FFFF),
                ansi = AnsiColors(
                    black = Color(0xFF000000), red = Color(0xFFAA0000), green = Color(0xFF00AA00), yellow = Color(0xFFAA5500),
                    blue = Color(0xFF0000AA), magenta = Color(0xFFAA00AA), cyan = Color(0xFF00AAAA), white = Color(0xFFAAAAAA),
                    brightBlack = Color(0xFF555555), brightRed = Color(0xFFFF5555), brightGreen = Color(0xFF55FF55), brightYellow = Color(0xFFFFFF55),
                    brightBlue = Color(0xFF5555FF), brightMagenta = Color(0xFFFF55FF), brightCyan = Color(0xFF55FFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 34. Windows Terminal
            TerminalTheme(
                id = "windows_terminal",
                name = "Windows Modern",
                category = ThemeCategory.Developer,
                background = Color(0xFF0C0C0C),
                foreground = Color(0xFFCCCCCC),
                cursor = Color(0xFFFFFFFF),
                selection = Color(0xFF3A3D41),
                accent = Color(0xFF0078D7),
                surface = Color(0xFF171717),
                border = Color(0xFF2D2D2D),
                aiIndicator = Color(0xFF13A10E),
                ansi = AnsiColors(
                    black = Color(0xFF0C0C0C), red = Color(0xFFC50F1F), green = Color(0xFF13A10E), yellow = Color(0xFFC19C00),
                    blue = Color(0xFF0037DA), magenta = Color(0xFF881798), cyan = Color(0xFF3A96DD), white = Color(0xFFCCCCCC),
                    brightBlack = Color(0xFF767676), brightRed = Color(0xFFE74856), brightGreen = Color(0xFF16C60C), brightYellow = Color(0xFFF9F1A5),
                    brightBlue = Color(0xFF3B78FF), brightMagenta = Color(0xFFB4009E), brightCyan = Color(0xFF61D6D6), brightWhite = Color(0xFFF2F2F2)
                )
            ),
            // 35. Ubuntu
            TerminalTheme(
                id = "ubuntu",
                name = "Ubuntu Purple",
                category = ThemeCategory.Developer,
                background = Color(0xFF300A24),
                foreground = Color(0xFFFFFFFF),
                cursor = Color(0xFFE95420),
                selection = Color(0xFF4C1D3F),
                accent = Color(0xFFE95420),
                surface = Color(0xFF24071B),
                border = Color(0xFF5E2750),
                aiIndicator = Color(0xFF77216F),
                ansi = AnsiColors(
                    black = Color(0xFF2E3436), red = Color(0xFFCC0000), green = Color(0xFF4E9A06), yellow = Color(0xFFC4A000),
                    blue = Color(0xFF3465A4), magenta = Color(0xFF75507B), cyan = Color(0xFF06989A), white = Color(0xFFD3D7CF),
                    brightBlack = Color(0xFF555753), brightRed = Color(0xFFEF2929), brightGreen = Color(0xFF8AE234), brightYellow = Color(0xFFFCE94F),
                    brightBlue = Color(0xFF729FCF), brightMagenta = Color(0xFFAD7FA8), brightCyan = Color(0xFF34E2E2), brightWhite = Color(0xFFEEEEEC)
                )
            ),
            // 36. Debian
            TerminalTheme(
                id = "debian",
                name = "Debian Swirl",
                category = ThemeCategory.Developer,
                background = Color(0xFF1D2026),
                foreground = Color(0xFFD8D8D8),
                cursor = Color(0xFFD70A53),
                selection = Color(0xFF323846),
                accent = Color(0xFFD70A53),
                surface = Color(0xFF16181D),
                border = Color(0xFF444B59),
                aiIndicator = Color(0xFFFF3377),
                ansi = AnsiColors(
                    black = Color(0xFF1D2026), red = Color(0xFFD70A53), green = Color(0xFF2E8B57), yellow = Color(0xFFE5A50A),
                    blue = Color(0xFF3584E4), magenta = Color(0xFF9141AC), cyan = Color(0xFF26A269), white = Color(0xFFD8D8D8),
                    brightBlack = Color(0xFF5E5C64), brightRed = Color(0xFFF66151), brightGreen = Color(0xFF33D17A), brightYellow = Color(0xFFF6D32D),
                    brightBlue = Color(0xFF62A0EA), brightMagenta = Color(0xFFC061CB), brightCyan = Color(0xFF33C7DE), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 37. Arch
            TerminalTheme(
                id = "arch",
                name = "Arch Cyan",
                category = ThemeCategory.Developer,
                background = Color(0xFF171A21),
                foreground = Color(0xFFEAEAEA),
                cursor = Color(0xFF1793D1),
                selection = Color(0xFF253342),
                accent = Color(0xFF1793D1),
                surface = Color(0xFF0F1217),
                border = Color(0xFF334A61),
                aiIndicator = Color(0xFF33AADD),
                ansi = AnsiColors(
                    black = Color(0xFF222831), red = Color(0xFFE05666), green = Color(0xFF48C774), yellow = Color(0xFFFFDD57),
                    blue = Color(0xFF1793D1), magenta = Color(0xFFB86BFF), cyan = Color(0xFF33B3EE), white = Color(0xFFDDE6ED),
                    brightBlack = Color(0xFF393E46), brightRed = Color(0xFFF07080), brightGreen = Color(0xFF5CD888), brightYellow = Color(0xFFFFE57A),
                    brightBlue = Color(0xFF38A9E6), brightMagenta = Color(0xFFC785FF), brightCyan = Color(0xFF55C7FA), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 38. Fedora
            TerminalTheme(
                id = "fedora",
                name = "Fedora Blue",
                category = ThemeCategory.Developer,
                background = Color(0xFF121E2C),
                foreground = Color(0xFFE3EBF4),
                cursor = Color(0xFF294172),
                selection = Color(0xFF1E3554),
                accent = Color(0xFF51A2DA),
                surface = Color(0xFF0A131C),
                border = Color(0xFF2A486C),
                aiIndicator = Color(0xFF3C6EB4),
                ansi = AnsiColors(
                    black = Color(0xFF1C2D42), red = Color(0xFFE23D3D), green = Color(0xFF43B581), yellow = Color(0xFFEAA030),
                    blue = Color(0xFF3C6EB4), magenta = Color(0xFF9E47A1), cyan = Color(0xFF51A2DA), white = Color(0xFFD4E1EE),
                    brightBlack = Color(0xFF364E6B), brightRed = Color(0xFFFF5C5C), brightGreen = Color(0xFF57CF99), brightYellow = Color(0xFFFFB84D),
                    brightBlue = Color(0xFF5C8DD1), brightMagenta = Color(0xFFBA60BD), brightCyan = Color(0xFF72BAEE), brightWhite = Color(0xFFF4F7FB)
                )
            ),
            // 39. Kali
            TerminalTheme(
                id = "kali",
                name = "Kali Dragon",
                category = ThemeCategory.Developer,
                background = Color(0xFF0D1117),
                foreground = Color(0xFFC9D1D9),
                cursor = Color(0xFF2777FF),
                selection = Color(0xFF1A273C),
                accent = Color(0xFF0066FF),
                surface = Color(0xFF070A0E),
                border = Color(0xFF1F355A),
                aiIndicator = Color(0xFF00C8FF),
                ansi = AnsiColors(
                    black = Color(0xFF161B22), red = Color(0xFFFF4455), green = Color(0xFF2EE688), yellow = Color(0xFFFFB833),
                    blue = Color(0xFF2777FF), magenta = Color(0xFFA352FF), cyan = Color(0xFF00C8FF), white = Color(0xFFC9D1D9),
                    brightBlack = Color(0xFF30363D), brightRed = Color(0xFFFF6677), brightGreen = Color(0xFF4FF09E), brightYellow = Color(0xFFFFC855),
                    brightBlue = Color(0xFF5292FF), brightMagenta = Color(0xFFB875FF), brightCyan = Color(0xFF38D5FF), brightWhite = Color(0xFFF0F6FC)
                )
            ),
            // 40. Mint
            TerminalTheme(
                id = "mint",
                name = "Mint Forest",
                category = ThemeCategory.Nature,
                background = Color(0xFF132018),
                foreground = Color(0xFFE0F0E6),
                cursor = Color(0xFF87CF3E),
                selection = Color(0xFF253E30),
                accent = Color(0xFF87CF3E),
                surface = Color(0xFF0C1610),
                border = Color(0xFF335742),
                aiIndicator = Color(0xFF98E645),
                ansi = AnsiColors(
                    black = Color(0xFF1A2E22), red = Color(0xFFE05252), green = Color(0xFF87CF3E), yellow = Color(0xFFD4B83E),
                    blue = Color(0xFF4FA8C4), magenta = Color(0xFFAB68C2), cyan = Color(0xFF4EC9B0), white = Color(0xFFD5E8DC),
                    brightBlack = Color(0xFF2F4F3B), brightRed = Color(0xFFF06A6A), brightGreen = Color(0xFF9EE848), brightYellow = Color(0xFFECD04A),
                    brightBlue = Color(0xFF6BC4E0), brightMagenta = Color(0xFFC27FDB), brightCyan = Color(0xFF6BE3C9), brightWhite = Color(0xFFF0FAF4)
                )
            ),
            // 41. Solar Flare
            TerminalTheme(
                id = "solar_flare",
                name = "Solar Flare",
                category = ThemeCategory.Neon,
                background = Color(0xFF1C0D02),
                foreground = Color(0xFFFFE0B2),
                cursor = Color(0xFFFF6D00),
                selection = Color(0xFF4E2002),
                accent = Color(0xFFFF3D00),
                surface = Color(0xFF120801),
                border = Color(0xFF7A3304),
                aiIndicator = Color(0xFFFF9100),
                ansi = AnsiColors(
                    black = Color(0xFF2E1504), red = Color(0xFFFF1744), green = Color(0xFFFFAB00), yellow = Color(0xFFFFD600),
                    blue = Color(0xFFFF6D00), magenta = Color(0xFFDD2C00), cyan = Color(0xFFFF9100), white = Color(0xFFFFE0B2),
                    brightBlack = Color(0xFF542607), brightRed = Color(0xFFFF5252), brightGreen = Color(0xFFFFC400), brightYellow = Color(0xFFFFEA00),
                    brightBlue = Color(0xFFFF851B), brightMagenta = Color(0xFFFF3D00), brightCyan = Color(0xFFFFA726), brightWhite = Color(0xFFFFF3E0)
                )
            ),
            // 42. Deep Space
            TerminalTheme(
                id = "deep_space",
                name = "Deep Space",
                category = ThemeCategory.Dark,
                background = Color(0xFF070B19),
                foreground = Color(0xFFC5CEE0),
                cursor = Color(0xFF598BFF),
                selection = Color(0xFF1A2138),
                accent = Color(0xFF3366FF),
                surface = Color(0xFF04060F),
                border = Color(0xFF222B45),
                aiIndicator = Color(0xFF00D68F),
                ansi = AnsiColors(
                    black = Color(0xFF151A30), red = Color(0xFFFF3D71), green = Color(0xFF00D68F), yellow = Color(0xFFFFAA00),
                    blue = Color(0xFF3366FF), magenta = Color(0xFF9040F5), cyan = Color(0xFF0095FF), white = Color(0xFFC5CEE0),
                    brightBlack = Color(0xFF2E3A59), brightRed = Color(0xFFFF708D), brightGreen = Color(0xFF2CE69B), brightYellow = Color(0xFFFFC94D),
                    brightBlue = Color(0xFF598BFF), brightMagenta = Color(0xFFA866FF), brightCyan = Color(0xFF33AAFF), brightWhite = Color(0xFFEDF1F7)
                )
            ),
            // 43. Galaxy
            TerminalTheme(
                id = "galaxy",
                name = "Andromeda Galaxy",
                category = ThemeCategory.Neon,
                background = Color(0xFF100C1F),
                foreground = Color(0xFFEAD8FF),
                cursor = Color(0xFFC084FC),
                selection = Color(0xFF2E1F4D),
                accent = Color(0xFFA855F7),
                surface = Color(0xFF090614),
                border = Color(0xFF4C2889),
                aiIndicator = Color(0xFFEC4899),
                ansi = AnsiColors(
                    black = Color(0xFF22173D), red = Color(0xFFF43F5E), green = Color(0xFF10B981), yellow = Color(0xFFFBBF24),
                    blue = Color(0xFF8B5CF6), magenta = Color(0xFFD946EF), cyan = Color(0xFF06B6D4), white = Color(0xFFEAD8FF),
                    brightBlack = Color(0xFF3B2966), brightRed = Color(0xFFFB7185), brightGreen = Color(0xFF34D399), brightYellow = Color(0xFFFDE68A),
                    brightBlue = Color(0xFFA78BFA), brightMagenta = Color(0xFFE879F9), brightCyan = Color(0xFF22D3EE), brightWhite = Color(0xFFFAF5FF)
                )
            ),
            // 44. Aurora
            TerminalTheme(
                id = "aurora",
                name = "Aurora Borealis",
                category = ThemeCategory.Nature,
                background = Color(0xFF0B191E),
                foreground = Color(0xFFD8F3DC),
                cursor = Color(0xFF52B788),
                selection = Color(0xFF1A3B3F),
                accent = Color(0xFF40916C),
                surface = Color(0xFF061014),
                border = Color(0xFF24575E),
                aiIndicator = Color(0xFF74C69D),
                ansi = AnsiColors(
                    black = Color(0xFF152A30), red = Color(0xFFEF476F), green = Color(0xFF52B788), yellow = Color(0xFFFFD166),
                    blue = Color(0xFF118AB2), magenta = Color(0xFF8338EC), cyan = Color(0xFF06D6A0), white = Color(0xFFD8F3DC),
                    brightBlack = Color(0xFF284852), brightRed = Color(0xFFF78CA4), brightGreen = Color(0xFF74C69D), brightYellow = Color(0xFFFFE099),
                    brightBlue = Color(0xFF3AA8CF), brightMagenta = Color(0xFFA56CF2), brightCyan = Color(0xFF3DE6B7), brightWhite = Color(0xFFF2FFF6)
                )
            ),
            // 45. Coffee
            TerminalTheme(
                id = "coffee",
                name = "Warm Espresso",
                category = ThemeCategory.Retro,
                background = Color(0xFF1E1714),
                foreground = Color(0xFFEDE0D4),
                cursor = Color(0xFFDDB892),
                selection = Color(0xFF3D2C24),
                accent = Color(0xFFB08968),
                surface = Color(0xFF140F0D),
                border = Color(0xFF563E32),
                aiIndicator = Color(0xFFE6CCB2),
                ansi = AnsiColors(
                    black = Color(0xFF2C211D), red = Color(0xFF9E2A2B), green = Color(0xFF7F9C66), yellow = Color(0xFFDDA15E),
                    blue = Color(0xFF7B6B5D), magenta = Color(0xFFA6634B), cyan = Color(0xFFB08968), white = Color(0xFFEDE0D4),
                    brightBlack = Color(0xFF473630), brightRed = Color(0xFFBA3839), brightGreen = Color(0xFF9BB782), brightYellow = Color(0xFFE8B87D),
                    brightBlue = Color(0xFF968677), brightMagenta = Color(0xFFC07E64), brightCyan = Color(0xFFC7A588), brightWhite = Color(0xFFFAF6F0)
                )
            ),
            // 46. Rose Pine
            TerminalTheme(
                id = "rose_pine",
                name = "Rosé Pine",
                category = ThemeCategory.Dark,
                background = Color(0xFF191724),
                foreground = Color(0xFFE0DEF4),
                cursor = Color(0xFFEBBCBA),
                selection = Color(0xFF2A283E),
                accent = Color(0xFFEB6F92),
                surface = Color(0xFF1F1D2E),
                border = Color(0xFF403D52),
                aiIndicator = Color(0xFF9CCFD8),
                ansi = AnsiColors(
                    black = Color(0xFF26233A), red = Color(0xFFEB6F92), green = Color(0xFF31748F), yellow = Color(0xFFF6C177),
                    blue = Color(0xFF9CCFD8), magenta = Color(0xFFC4A7E7), cyan = Color(0xFFEBBCBA), white = Color(0xFFE0DEF4),
                    brightBlack = Color(0xFF524F67), brightRed = Color(0xFFEB6F92), brightGreen = Color(0xFF31748F), brightYellow = Color(0xFFF6C177),
                    brightBlue = Color(0xFF9CCFD8), brightMagenta = Color(0xFFC4A7E7), brightCyan = Color(0xFFEBBCBA), brightWhite = Color(0xFFE0DEF4)
                )
            ),
            // 47. Everforest
            TerminalTheme(
                id = "everforest",
                name = "Everforest Dark",
                category = ThemeCategory.Nature,
                background = Color(0xFF2D353B),
                foreground = Color(0xFFD3C6AA),
                cursor = Color(0xFFA7C080),
                selection = Color(0xFF425047),
                accent = Color(0xFFA7C080),
                surface = Color(0xFF232A2E),
                border = Color(0xFF4C5656),
                aiIndicator = Color(0xFF7FBBB3),
                ansi = AnsiColors(
                    black = Color(0xFF343F44), red = Color(0xFFE67E80), green = Color(0xFFA7C080), yellow = Color(0xFFDBBC7F),
                    blue = Color(0xFF7FBBB3), magenta = Color(0xFFD699B6), cyan = Color(0xFF83C092), white = Color(0xFFD3C6AA),
                    brightBlack = Color(0xFF4F5B58), brightRed = Color(0xFFE67E80), brightGreen = Color(0xFFA7C080), brightYellow = Color(0xFFDBBC7F),
                    brightBlue = Color(0xFF7FBBB3), brightMagenta = Color(0xFFD699B6), brightCyan = Color(0xFF83C092), brightWhite = Color(0xFFD3C6AA)
                )
            ),
            // 48. Kanagawa
            TerminalTheme(
                id = "kanagawa",
                name = "Kanagawa Wave",
                category = ThemeCategory.Dark,
                background = Color(0xFF1F1F28),
                foreground = Color(0xFFDCD7BA),
                cursor = Color(0xFFC8C093),
                selection = Color(0xFF2D4F67),
                accent = Color(0xFF7E9CD8),
                surface = Color(0xFF16161D),
                border = Color(0xFF363646),
                aiIndicator = Color(0xFF98BB6C),
                ansi = AnsiColors(
                    black = Color(0xFF223249), red = Color(0xFFC34043), green = Color(0xFF76946A), yellow = Color(0xFFC0A36E),
                    blue = Color(0xFF7E9CD8), magenta = Color(0xFF957FB8), cyan = Color(0xFF6A9589), white = Color(0xFFDCD7BA),
                    brightBlack = Color(0xFF54546D), brightRed = Color(0xFFE82424), brightGreen = Color(0xFF98BB6C), brightYellow = Color(0xFFE6C384),
                    brightBlue = Color(0xFF7FB4CA), brightMagenta = Color(0xFF938AA9), brightCyan = Color(0xFF7AA89F), brightWhite = Color(0xFFC8C093)
                )
            ),
            // 49. Ayu Dark
            TerminalTheme(
                id = "ayu_dark",
                name = "Ayu Dark",
                category = ThemeCategory.Developer,
                background = Color(0xFF0F141C),
                foreground = Color(0xFFB3B1AD),
                cursor = Color(0xFFE6B450),
                selection = Color(0xFF253340),
                accent = Color(0xFFFFB454),
                surface = Color(0xFF0A0E14),
                border = Color(0xFF23303D),
                aiIndicator = Color(0xFF7FD962),
                ansi = AnsiColors(
                    black = Color(0xFF1A232D), red = Color(0xFFFF3333), green = Color(0xFFC2D94C), yellow = Color(0xFFE6B450),
                    blue = Color(0xFF59C2FF), magenta = Color(0xFFD4BFFF), cyan = Color(0xFF95E6CB), white = Color(0xFFB3B1AD),
                    brightBlack = Color(0xFF394654), brightRed = Color(0xFFFF6666), brightGreen = Color(0xFFD5E86E), brightYellow = Color(0xFFFFD173),
                    brightBlue = Color(0xFF73D0FF), brightMagenta = Color(0xFFDFCCFF), brightCyan = Color(0xFFB8F0DE), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 50. Ayu Light
            TerminalTheme(
                id = "ayu_light",
                name = "Ayu Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFAFAFA),
                foreground = Color(0xFF5C6166),
                cursor = Color(0xFFFF6A00),
                selection = Color(0xFFE7E8E9),
                accent = Color(0xFFFF6A00),
                surface = Color(0xFFF0F0F0),
                border = Color(0xFFD6D7D9),
                aiIndicator = Color(0xFF86B300),
                ansi = AnsiColors(
                    black = Color(0xFF5C6166), red = Color(0xFFF07178), green = Color(0xFF86B300), yellow = Color(0xFFFA8D3E),
                    blue = Color(0xFF399EE6), magenta = Color(0xFFA37ACC), cyan = Color(0xFF4CBF99), white = Color(0xFFABB0B6),
                    brightBlack = Color(0xFF8A9199), brightRed = Color(0xFFF58A90), brightGreen = Color(0xFF9DC424), brightYellow = Color(0xFFFFA05C),
                    brightBlue = Color(0xFF55B5F5), brightMagenta = Color(0xFFB794DB), brightCyan = Color(0xFF68D4B0), brightWhite = Color(0xFF32363A)
                )
            ),
            // 51. Material Dark
            TerminalTheme(
                id = "material_dark",
                name = "Material You Dark",
                category = ThemeCategory.Developer,
                background = Color(0xFF141218),
                foreground = Color(0xFFE6E0E9),
                cursor = Color(0xFFD0BCFF),
                selection = Color(0xFF332D41),
                accent = Color(0xFFD0BCFF),
                surface = Color(0xFF1D1B20),
                border = Color(0xFF49454F),
                aiIndicator = Color(0xFFCCC2DC),
                ansi = AnsiColors(
                    black = Color(0xFF211F26), red = Color(0xFFF2B8B5), green = Color(0xFFA8DAB5), yellow = Color(0xFFFFD56B),
                    blue = Color(0xFFD0BCFF), magenta = Color(0xFFEFB8C8), cyan = Color(0xFF8FD8D8), white = Color(0xFFE6E0E9),
                    brightBlack = Color(0xFF49454F), brightRed = Color(0xFFF9DEDC), brightGreen = Color(0xFFC4EED0), brightYellow = Color(0xFFFFE799),
                    brightBlue = Color(0xFFE8DEF8), brightMagenta = Color(0xFFFFD8E4), brightCyan = Color(0xFFB8EEEE), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 52. Material Light
            TerminalTheme(
                id = "material_light",
                name = "Material You Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFEF7FF),
                foreground = Color(0xFF1D1B20),
                cursor = Color(0xFF6750A4),
                selection = Color(0xFFEADDFF),
                accent = Color(0xFF6750A4),
                surface = Color(0xFFF3EDF7),
                border = Color(0xFF79747E),
                aiIndicator = Color(0xFF7D5260),
                ansi = AnsiColors(
                    black = Color(0xFF1D1B20), red = Color(0xFFB3261E), green = Color(0xFF286D43), yellow = Color(0xFF7B5A00),
                    blue = Color(0xFF6750A4), magenta = Color(0xFF7D5260), cyan = Color(0xFF006874), white = Color(0xFF49454F),
                    brightBlack = Color(0xFF79747E), brightRed = Color(0xFFDC362D), brightGreen = Color(0xFF388E5A), brightYellow = Color(0xFF9E7400),
                    brightBlue = Color(0xFF7F67BE), brightMagenta = Color(0xFF986979), brightCyan = Color(0xFF008394), brightWhite = Color(0xFF000000)
                )
            ),
            // 53. GitHub Dark
            TerminalTheme(
                id = "github_dark",
                name = "GitHub Dark",
                category = ThemeCategory.Developer,
                background = Color(0xFF0D1117),
                foreground = Color(0xFFC9D1D9),
                cursor = Color(0xFF58A6FF),
                selection = Color(0xFF163C61),
                accent = Color(0xFF1F6FEB),
                surface = Color(0xFF161B22),
                border = Color(0xFF30363D),
                aiIndicator = Color(0xFF238636),
                ansi = AnsiColors(
                    black = Color(0xFF484F58), red = Color(0xFFFF7B72), green = Color(0xFF3FB950), yellow = Color(0xFFD29922),
                    blue = Color(0xFF58A6FF), magenta = Color(0xFFBC8CFF), cyan = Color(0xFF39C5CF), white = Color(0xFFB1BAC4),
                    brightBlack = Color(0xFF6E7681), brightRed = Color(0xFFFFA198), brightGreen = Color(0xFF56D364), brightYellow = Color(0xFFE3B341),
                    brightBlue = Color(0xFF79C0FF), brightMagenta = Color(0xFFD2A8FF), brightCyan = Color(0xFF56D4DD), brightWhite = Color(0xFFF0F6FC)
                )
            ),
            // 54. GitHub Light
            TerminalTheme(
                id = "github_light",
                name = "GitHub Light",
                category = ThemeCategory.Light,
                background = Color(0xFFFFFFFF),
                foreground = Color(0xFF24292F),
                cursor = Color(0xFF0969DA),
                selection = Color(0xFFB6E3FF),
                accent = Color(0xFF0969DA),
                surface = Color(0xFFF6F8FA),
                border = Color(0xFFD0D7DE),
                aiIndicator = Color(0xFF1A7F37),
                ansi = AnsiColors(
                    black = Color(0xFF24292F), red = Color(0xFFCF222E), green = Color(0xFF116329), yellow = Color(0xFF4D2D00),
                    blue = Color(0xFF0969DA), magenta = Color(0xFF8250DF), cyan = Color(0xFF1B7C83), white = Color(0xFF6E7781),
                    brightBlack = Color(0xFF57606A), brightRed = Color(0xFFA40E26), brightGreen = Color(0xFF1A7F37), brightYellow = Color(0xFF633C01),
                    brightBlue = Color(0xFF218BFF), brightMagenta = Color(0xFFA475F9), brightCyan = Color(0xFF3192AA), brightWhite = Color(0xFF8C959F)
                )
            ),
            // 55. High Contrast
            TerminalTheme(
                id = "high_contrast",
                name = "Ultra High Contrast",
                category = ThemeCategory.HighContrast,
                background = Color(0xFF000000),
                foreground = Color(0xFFFFFFFF),
                cursor = Color(0xFFFFFF00),
                selection = Color(0xFF0000FF),
                accent = Color(0xFF00FFFF),
                surface = Color(0xFF080808),
                border = Color(0xFFFFFFFF),
                aiIndicator = Color(0xFFFFFF00),
                ansi = AnsiColors(
                    black = Color(0xFF000000), red = Color(0xFFFF0000), green = Color(0xFF00FF00), yellow = Color(0xFFFFFF00),
                    blue = Color(0xFF0088FF), magenta = Color(0xFFFF00FF), cyan = Color(0xFF00FFFF), white = Color(0xFFFFFFFF),
                    brightBlack = Color(0xFF888888), brightRed = Color(0xFFFF5555), brightGreen = Color(0xFF55FF55), brightYellow = Color(0xFFFFFF55),
                    brightBlue = Color(0xFF55AAFF), brightMagenta = Color(0xFFFF55FF), brightCyan = Color(0xFF55FFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 56. Paper
            TerminalTheme(
                id = "paper",
                name = "Vintage Paper",
                category = ThemeCategory.Light,
                background = Color(0xFFF4ECD8),
                foreground = Color(0xFF3E3129),
                cursor = Color(0xFF8B4513),
                selection = Color(0xFFE2D4B7),
                accent = Color(0xFF8B4513),
                surface = Color(0xFFEADFC6),
                border = Color(0xFFC7B695),
                aiIndicator = Color(0xFF5C4033),
                ansi = AnsiColors(
                    black = Color(0xFF3E3129), red = Color(0xFFA72626), green = Color(0xFF437A3C), yellow = Color(0xFFB57C1E),
                    blue = Color(0xFF2C5D88), magenta = Color(0xFF7E3878), cyan = Color(0xFF317577), white = Color(0xFF948574),
                    brightBlack = Color(0xFF6B5A4E), brightRed = Color(0xFFC93B3B), brightGreen = Color(0xFF569B4D), brightYellow = Color(0xFFD69832),
                    brightBlue = Color(0xFF3E7EB8), brightMagenta = Color(0xFFA1499A), brightCyan = Color(0xFF42999C), brightWhite = Color(0xFF231B15)
                )
            ),
            // 57. Chalk
            TerminalTheme(
                id = "chalk",
                name = "Chalkboard",
                category = ThemeCategory.Retro,
                background = Color(0xFF242E28),
                foreground = Color(0xFFF2F4F3),
                cursor = Color(0xFFFFD166),
                selection = Color(0xFF37463E),
                accent = Color(0xFF06D6A0),
                surface = Color(0xFF1C2420),
                border = Color(0xFF4A5D53),
                aiIndicator = Color(0xFFEF476F),
                ansi = AnsiColors(
                    black = Color(0xFF313D36), red = Color(0xFFEF476F), green = Color(0xFF06D6A0), yellow = Color(0xFFFFD166),
                    blue = Color(0xFF118AB2), magenta = Color(0xFFC77DFF), cyan = Color(0xFF70D6FF), white = Color(0xFFF2F4F3),
                    brightBlack = Color(0xFF4E6156), brightRed = Color(0xFFF77292), brightGreen = Color(0xFF38E3B6), brightYellow = Color(0xFFFFE094),
                    brightBlue = Color(0xFF31A6CD), brightMagenta = Color(0xFFD69EFF), brightCyan = Color(0xFF9BE1FF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 58. Cyber Blue
            TerminalTheme(
                id = "cyber_blue",
                name = "Cyber Blue",
                category = ThemeCategory.Neon,
                background = Color(0xFF080D1A),
                foreground = Color(0xFFD0E2FF),
                cursor = Color(0xFF0072FF),
                selection = Color(0xFF122442),
                accent = Color(0xFF00C6FF),
                surface = Color(0xFF0F172A),
                border = Color(0xFF1E3A6D),
                aiIndicator = Color(0xFF00F0FF),
                ansi = AnsiColors(
                    black = Color(0xFF101B30), red = Color(0xFFFF3366), green = Color(0xFF00FFB2), yellow = Color(0xFFFFCC00),
                    blue = Color(0xFF0088FF), magenta = Color(0xFFB026FF), cyan = Color(0xFF00EAFF), white = Color(0xFFD0E2FF),
                    brightBlack = Color(0xFF263C66), brightRed = Color(0xFFFF668C), brightGreen = Color(0xFF44FFC8), brightYellow = Color(0xFFFFDD44),
                    brightBlue = Color(0xFF44AAFF), brightMagenta = Color(0xFFC85EFF), brightCyan = Color(0xFF55EFFF), brightWhite = Color(0xFFFFFFFF)
                )
            ),
            // 59. Crimson
            TerminalTheme(
                id = "crimson",
                name = "Crimson Dark",
                category = ThemeCategory.Dark,
                background = Color(0xFF180A0A),
                foreground = Color(0xFFFFDCDC),
                cursor = Color(0xFFFF1A40),
                selection = Color(0xFF3D1418),
                accent = Color(0xFFFF2E51),
                surface = Color(0xFF100505),
                border = Color(0xFF5C1B23),
                aiIndicator = Color(0xFFFF5277),
                ansi = AnsiColors(
                    black = Color(0xFF2A1013), red = Color(0xFFFF1A40), green = Color(0xFF38B000), yellow = Color(0xFFFFB703),
                    blue = Color(0xFF9D4EDD), magenta = Color(0xFFFF0054), cyan = Color(0xFFFF5470), white = Color(0xFFFFDCDC),
                    brightBlack = Color(0xFF4F1E24), brightRed = Color(0xFFFF4D6D), brightGreen = Color(0xFF52C41A), brightYellow = Color(0xFFFFC933),
                    brightBlue = Color(0xFFB76EF0), brightMagenta = Color(0xFFFF3377), brightCyan = Color(0xFFFF8599), brightWhite = Color(0xFFFFF0F0)
                )
            ),
            // 60. Emerald
            TerminalTheme(
                id = "emerald",
                name = "Emerald City",
                category = ThemeCategory.Nature,
                background = Color(0xFF0B1914),
                foreground = Color(0xFFD8F3DC),
                cursor = Color(0xFF10B981),
                selection = Color(0xFF173D2F),
                accent = Color(0xFF059669),
                surface = Color(0xFF06110D),
                border = Color(0xFF1F523F),
                aiIndicator = Color(0xFF34D399),
                ansi = AnsiColors(
                    black = Color(0xFF132D24), red = Color(0xFFF43F5E), green = Color(0xFF10B981), yellow = Color(0xFFFBBF24),
                    blue = Color(0xFF0EA5E9), magenta = Color(0xFF8B5CF6), cyan = Color(0xFF14B8A6), white = Color(0xFFD8F3DC),
                    brightBlack = Color(0xFF234F3F), brightRed = Color(0xFFFB7185), brightGreen = Color(0xFF34D399), brightYellow = Color(0xFFFCD34D),
                    brightBlue = Color(0xFF38BDF8), brightMagenta = Color(0xFFA78BFA), brightCyan = Color(0xFF2DD4BF), brightWhite = Color(0xFFF0FDF4)
                )
            )
        )
    }

    private val themeMap: Map<String, TerminalTheme> by lazy {
        allThemes.associateBy { it.id }
    }

    fun getTheme(id: String): TerminalTheme {
        return themeMap[id] ?: allThemes.first()
    }
}
