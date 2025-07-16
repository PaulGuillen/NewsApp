package com.devpaul.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.home.ui.acknowledgments.AcknowledgmentScreen
import com.devpaul.home.ui.home.HomeScreen

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
) {
    composable(Screen.Home.route) {
        HomeScreen(navController)
    }

    composable(Screen.Acknowledgment.route) {
        AcknowledgmentScreen(navController)
    }
}