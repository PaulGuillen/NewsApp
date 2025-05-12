package com.devpaul.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.auth.ui.login.components.LoginScreen
import com.devpaul.core_domain.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Login.route) {
       LoginScreen(navController)
    }
}