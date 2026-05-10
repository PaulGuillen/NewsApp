package com.devpaul.auth.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.ProfileDarkMuted
import com.devpaul.core_platform.theme.ProfileDarkText
import com.devpaul.core_platform.theme.White

@Immutable
data class AuthUiColors(
    val background: Brush,
    val backgroundSolid: Color,
    val card: Color,
    val cardBorder: Color,
    val title: Color,
    val body: Color,
    val accent: Color,
    val accentSoft: Color,
    val buttonContainer: Color,
    val buttonContent: Color,
    val buttonBorder: Color,
    val buttonGlow: Color,
    val link: Color,
    val checkboxUnchecked: Color,
)

@Composable
fun rememberAuthUiColors(): AuthUiColors {
    val isDark = isSystemInDarkTheme()

    return remember(isDark) {
        if (isDark) {
            AuthUiColors(
                background = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF08111B),
                        Color(0xFF0B1724),
                        Black
                    )
                ),
                backgroundSolid = Color(0xFF08111B),
                card = Color(0xCC101A26),
                cardBorder = BrandAccent.copy(alpha = 0.34f),
                title = ProfileDarkText,
                body = Color(0xFF9EACC0),
                accent = BrandAccent,
                accentSoft = BrandAccent.copy(alpha = 0.16f),
                buttonContainer = Color(0xFF083A44),
                buttonContent = BrandAccent,
                buttonBorder = BrandAccent.copy(alpha = 0.92f),
                buttonGlow = BrandAccent.copy(alpha = 0.46f),
                link = BrandAccent,
                checkboxUnchecked = ProfileDarkMuted
            )
        } else {
            AuthUiColors(
                background = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFDFBFB),
                        Color(0xFFF6F1F1),
                        Color(0xFFFCFCFC)
                    )
                ),
                backgroundSolid = Color(0xFFFDFBFB),
                card = White.copy(alpha = 0.96f),
                cardBorder = Color(0xFFD7CFCF),
                title = Color(0xFF2B2730),
                body = Color(0xFF5C5662),
                accent = BrickRed,
                accentSoft = BrickRed.copy(alpha = 0.08f),
                buttonContainer = BrickRed,
                buttonContent = White,
                buttonBorder = Color.Transparent,
                buttonGlow = BrickRed.copy(alpha = 0.16f),
                link = BrickRed,
                checkboxUnchecked = Color(0xFF2B2730)
            )
        }
    }
}