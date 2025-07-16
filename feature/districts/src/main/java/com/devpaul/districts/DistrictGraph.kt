package com.devpaul.districts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.districts.ui.DistrictScreen

fun NavGraphBuilder.districtGraph(
    navController: NavHostController,
) {
    composable(Screen.Districts.route) {
        DistrictScreen(navController)
    }
}