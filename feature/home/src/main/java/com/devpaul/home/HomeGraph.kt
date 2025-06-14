package com.devpaul.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.home.ui.HomeScreen
import com.devpaul.navigation.core.jetpack.AppNavigator

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }
}