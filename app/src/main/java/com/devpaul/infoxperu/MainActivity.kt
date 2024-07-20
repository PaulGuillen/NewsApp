package com.devpaul.infoxperu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.infoxperu.ui.theme.InfoXPeruTheme
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.feature.user_start.StartNavHost
import com.devpaul.infoxperu.feature.user_start.login.LoginScreen
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfoXPeruTheme {
        LoginScreen(navController = rememberNavController())
    }
}
