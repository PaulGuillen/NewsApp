package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.feature.user_start.StartNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InfoXPeruTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    StartNavHost(navController)
                }
            }
        }
    }
}