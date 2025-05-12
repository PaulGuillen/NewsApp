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
    primary = BrickRed, // Color principal en modo oscuro
    onPrimary = White, // Color del texto sobre color principal en modo oscuro
    secondary = BrickRed,
    onSecondary = White,
    tertiary = White,
    background = Black, // Color de fondo en modo oscuro
    surface = Color.DarkGray // Color de las superficies (tarjetas) en modo oscuro
)

private val LightColorScheme = lightColorScheme(
    primary = BrickRed, // Color principal en modo claro
    onPrimary = White, // Color del texto sobre color principal en modo claro
    secondary = BrickRed,
    onSecondary = White,
    tertiary = Color.DarkGray, // Color terciario en modo claro
    background = White, // Color de fondo en modo claro
    surface = White // Color de las superficies (tarjetas) en modo claro
)

@Composable
fun InfoXPeruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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