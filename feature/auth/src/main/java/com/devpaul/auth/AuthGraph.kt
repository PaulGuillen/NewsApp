package com.devpaul.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.auth.ui.login.LoginScreen
import com.devpaul.auth.ui.register.RegisterScreen
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Login.route) {
        LoginScreen(navHostController = navController, appNavigator = appNavigator)
    }
    composable(Screen.Register.route) {
        RegisterScreen(navHostController = navController, appNavigator = appNavigator)
    }
}