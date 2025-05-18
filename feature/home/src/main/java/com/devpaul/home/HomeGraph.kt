package com.devpaul.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_domain.Screen
import com.devpaul.home.ui.home.HomeScreen
import com.devpaul.navigation.core.jetpack.AppNavigator

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
}