package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.domain.extension.setStatusBarColor
import com.devpaul.infoxperu.feature.StartNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val statusBarColor = if (isDarkTheme) Color.Black else Color.White
            setStatusBarColor(statusBarColor, darkIcons = !isDarkTheme)
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                StartNavHost(navController)
            }
        }
    }

}
