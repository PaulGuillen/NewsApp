package com.devpaul.districts

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.districts.ui.DistrictScreen
import com.devpaul.navigation.core.jetpack.AppNavigator

fun NavGraphBuilder.districtGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Districts.route) {
        DistrictScreen(navController)
    }
}