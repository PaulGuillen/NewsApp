package com.devpaul.navigation.core.jetpack

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.core.parameter.parametersOf

@Composable
fun MainGraph(navController: NavHostController, appNavigator: AppNavigator) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
        }
        composable(Screen.Register.route) {
            // ...
        }
        composable(Screen.Home.route) {
            // ...
        }
    }
}
