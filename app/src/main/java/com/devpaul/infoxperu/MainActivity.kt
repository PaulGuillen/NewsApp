package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.SetStatusBarColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val statusBarColor = if (isDarkTheme) Color.Black else Color.White
            SetStatusBarColor(statusBarColor, darkIcons = !isDarkTheme)
            InfoXPeruTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false,
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    MainGraph(navController = navController)
                }
            }
        }
    }
}