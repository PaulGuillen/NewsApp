package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.domain.extension.setStatusBarColor
import com.devpaul.navigation.core.jetpack.AppNavigator
import dagger.hilt.android.AndroidEntryPoint
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.getKoin

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
                val appNavigator: AppNavigator = remember {
                    getKoin().get<AppNavigator> { parametersOf(navController) }
                }
                MainGraph(navController = navController, appNavigator = appNavigator)
            }
        }
    }
}