package com.devpaul.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.auth.ui.login.LoginScreen
import com.devpaul.auth.ui.register.RegisterScreen
import com.devpaul.core_data.Screen

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    composable(Screen.Login.route) {
        LoginScreen(navHostController = navController)
    }
    composable(Screen.Register.route) {
        RegisterScreen(navHostController = navController)
    }
}