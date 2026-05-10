package com.devpaul.profile.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.EmergencyDark
import com.devpaul.core_platform.theme.EmergencyLight
import com.devpaul.core_platform.theme.EmergencyRed
import com.devpaul.core_platform.theme.ProfileDarkAccent
import com.devpaul.core_platform.theme.ProfileDarkCard
import com.devpaul.core_platform.theme.ProfileDarkMuted
import com.devpaul.core_platform.theme.ProfileDarkText

@Immutable
data class ProfileUiColors(
    val background: Color,
    val surface: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val accent: Color,
    val outline: Color,
    val actionContainer: Color,
    val actionContent: Color,
    val actionBorder: Color,
)

@Composable
fun rememberProfileUiColors(): ProfileUiColors {
    val isDark = isSystemInDarkTheme()
    val colorScheme = MaterialTheme.colorScheme

    return remember(isDark, colorScheme) {
        ProfileUiColors(
            background = if (isDark) Black else colorScheme.background,
            surface = if (isDark) ProfileDarkCard else colorScheme.surface,
            primaryText = if (isDark) ProfileDarkText else colorScheme.tertiaryContainer,
            secondaryText = if (isDark) ProfileDarkMuted else colorScheme.onSurfaceVariant,
            accent = if (isDark) ProfileDarkAccent else colorScheme.primary,
            outline = if (isDark) ProfileDarkCard else colorScheme.outlineVariant,
            actionContainer = if (isDark) EmergencyDark else EmergencyLight,
            actionContent = EmergencyRed,
            actionBorder = if (isDark) EmergencyRed.copy(alpha = 0.35f) else EmergencyRed.copy(alpha = 0.18f)
        )
    }
}
