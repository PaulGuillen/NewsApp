package com.devpaul.infoxperu.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BrickRed,
    onPrimary = White, // Color del texto del botón en modo claro
    secondary = BrickRed,
    onSecondary = White, // Color del texto del botón en modo claro
    tertiary = White,
    background = Black,
    surface = Gray
)

private val LightColorScheme = lightColorScheme(
    primary = BrickRed,
    onPrimary = White, // Color del texto del botón en modo claro
    secondary = BrickRed,
    onSecondary = White, // Color del texto del botón en modo claro
    tertiary = White,
    background = White,
    surface = Gray
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