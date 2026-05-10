package com.devpaul.shared.ui.components.atoms.base.textfield

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.BrandAccent
import com.devpaul.core_platform.theme.ProfileDarkCard
import com.devpaul.core_platform.theme.ProfileDarkMuted
import com.devpaul.core_platform.theme.ProfileDarkText

@Composable
fun profileFieldColors(): TextFieldColors {
    val isDark = isSystemInDarkTheme()
    val accent = if (isDark) BrandAccent else BrickRed

    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = accent,
        unfocusedBorderColor = if (isDark) ProfileDarkMuted else MaterialTheme.colorScheme.outline,
        disabledBorderColor = if (isDark) ProfileDarkMuted.copy(alpha = 0.7f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
        focusedLabelColor = accent,
        unfocusedLabelColor = if (isDark) Color.White.copy(alpha = 0.72f) else MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLabelColor = if (isDark) Color.White.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        cursorColor = accent,
        focusedLeadingIconColor = accent,
        unfocusedLeadingIconColor = if (isDark) Color.White.copy(alpha = 0.72f) else MaterialTheme.colorScheme.onSurfaceVariant,
        disabledLeadingIconColor = if (isDark) Color.White.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        focusedTrailingIconColor = accent,
        unfocusedTrailingIconColor = if (isDark) Color.White.copy(alpha = 0.72f) else MaterialTheme.colorScheme.onSurfaceVariant,
        disabledTrailingIconColor = if (isDark) Color.White.copy(alpha = 0.5f) else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        focusedTextColor = if (isDark) ProfileDarkText else MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = if (isDark) ProfileDarkText else MaterialTheme.colorScheme.onSurface,
        disabledTextColor = if (isDark) ProfileDarkText.copy(alpha = 0.75f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        focusedContainerColor = if (isDark) ProfileDarkCard else Color.Transparent,
        unfocusedContainerColor = if (isDark) ProfileDarkCard else Color.Transparent,
        disabledContainerColor = if (isDark) ProfileDarkCard.copy(alpha = 0.9f) else Color.Transparent
    )
}