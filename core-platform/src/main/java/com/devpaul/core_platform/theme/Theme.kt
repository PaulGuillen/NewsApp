package com.devpaul.core_platform.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BrandAccent,
    onPrimary = Black,
    primaryContainer = Black,
    onPrimaryContainer = BrandAccent, // Color de fondo para elementos que contienen `primary` (botones elevados, etc.)
    secondary = BrickRedLight,
    onSecondary = Black,
    tertiary = White,
    onTertiary = White,
    tertiaryContainer = White,
    onTertiaryContainer = BrandAccent,
    background = Black,
    onBackground = ProfileDarkText,
    surface = ProfileDarkBackground,
    onSurface = ProfileDarkText,
    surfaceVariant = ProfileDarkCard,
    onSurfaceVariant = Color(0xFFB9C6D8),
    surfaceContainerLowest = Black,
    surfaceContainerLow = ProfileDarkBackground,
    surfaceContainer = ProfileDarkBackground,
    surfaceContainerHigh = ProfileDarkCard,
    surfaceContainerHighest = ProfileDarkCard,
    outline = Color(0xFF8996A8),
    outlineVariant = Color(0xFF3C4B5F)
)

private val LightColorScheme = lightColorScheme(
    primary = BrickRed,         // Color principal de la marca en modo claro
    onPrimary = White,
    primaryContainer = PinkGray,
    onPrimaryContainer = Black, // Color de fondo para elementos que contienen `primary`
    secondary = BrickRed,       // Color secundario para distinguir otras acciones o secciones
    onSecondary = White,
    tertiary = InfoIcon,
    onTertiary = TextSecondary,
    tertiaryContainer = Black,
    onTertiaryContainer = Blue500,
    background = White,         // Color de fondo general de la app en modo claro
    onBackground = TextSecondary,
    surface = White,
    onSurface = TextSecondary,
    surfaceVariant = Taupe,
    onSurfaceVariant = Color(0xFF4B5563),
    surfaceContainerLowest = White,
    surfaceContainerLow = White,
    surfaceContainer = Taupe,
    surfaceContainerHigh = Taupe,
    surfaceContainerHighest = Taupe,
    outline = Color(0xFF6B7280),
    outlineVariant = Color(0xFFD1D5DB)

)

@Composable
fun InfoXPeruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
