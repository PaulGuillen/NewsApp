package com.devpaul.emergency

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devpaul.core_data.Screen
import com.devpaul.emergency.ui.details.DetailsScreen
import com.devpaul.emergency.ui.emergency.EmergencyScreen

fun NavGraphBuilder.emergencyGraph(
    navController: NavHostController,
) {
    composable(Screen.Emergency.route) {
        EmergencyScreen(navHostController = navController)
    }

    composable(
        route = Screen.EmergencyDetail.route,
        arguments = listOf(
            navArgument("type") { type = NavType.StringType },
        )
    ) { backStackEntry ->
        DetailsScreen(
            navController = navController,
            type = backStackEntry.arguments?.getString("type"),
        )
    }

}