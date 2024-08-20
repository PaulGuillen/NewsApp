package com.devpaul.infoxperu.domain.extension

import android.app.Activity
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

fun Activity.setStatusBarColor(color: Color, darkIcons: Boolean = false) {
    val window: Window = window
    window.statusBarColor = color.toArgb()
    window.navigationBarColor = color.toArgb()
    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = darkIcons
}

@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean = false) {
    val context = LocalContext.current
    val activity = context as? Activity
    activity?.setStatusBarColor(color, darkIcons)
}

/**Como usar en tu Screen Principal
 *   val isDarkTheme = isSystemInDarkTheme()
 *   SetStatusBarColor(
 *         color = if (isDarkTheme) Color.Black else Color.White,
 *         darkIcons = !isDarkTheme
 *   )
 * */