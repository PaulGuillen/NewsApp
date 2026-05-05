package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.SetStatusBarColor
import com.devpaul.infoxperu.ui.ForceUpdateScreen
import com.devpaul.infoxperu.ui.UpdateChecker
import com.devpaul.infoxperu.ui.openPlayStore

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
                    val updateRequired = remember { mutableStateOf<Boolean?>(null) }
                    LaunchedEffect(Unit) {
                        try {
                            val required = UpdateChecker.isForceUpdateRequired(this@MainActivity)
                            updateRequired.value = required
                        } catch (_: Exception) {
                            updateRequired.value = false
                        }
                    }

                    if (updateRequired.value == true) {
                        ForceUpdateScreen(onOpenStore = { openPlayStore(this@MainActivity) })
                    } else {
                        MainGraph(navController = navController)
                    }
                }
            }
        }
    }
}