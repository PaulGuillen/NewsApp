package com.devpaul.emergency

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

import com.devpaul.core_data.Screen
import com.example.emergency.ui.emergency.EmergencyScreen

fun NavGraphBuilder.emergencyGraph(
    navController: NavHostController,
) {
    composable(Screen.Emergency.route) {
        EmergencyScreen(navHostController = navController)
    }
}