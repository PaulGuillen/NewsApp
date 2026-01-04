package com.devpaul.core_platform.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BrickRed,         // Color principal de la marca (usado en botones, switches, barras activas)
    onPrimary = White,          // Color del texto o íconos que van sobre el color `primary`
    secondary = BrickRed,       // Color secundario para componentes menos destacados o alternativos
    onSecondary = White,        // Color del contenido sobre `secondary`
    tertiary = White,           // Color terciario (opcional, usado para acentos o elementos gráficos adicionales)
    background = Black,         // Color de fondo general (por ejemplo, detrás del contenido principal)
    surface = Black // Color de superficies como tarjetas, diálogos, sheets en modo oscuro
)

private val LightColorScheme = lightColorScheme(
    primary = BrickRed,         // Color principal de la marca en modo claro
    onPrimary = White,          // Color del texto o íconos sobre `primary` (por ejemplo en un botón rojo)
    secondary = BrickRed,       // Color secundario para distinguir otras acciones o secciones
    onSecondary = DarkGray,     // Color del texto o íconos sobre `secondary`
    tertiary = LightGray,        // Color opcional para detalles visuales adicionales (iconos decorativos, etc.)
    background = White,         // Color de fondo general de la app en modo claro
    surface = White             // Color de tarjetas, diálogos y contenedores que descansan sobre el fondo
)

@Composable
fun InfoXPeruTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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