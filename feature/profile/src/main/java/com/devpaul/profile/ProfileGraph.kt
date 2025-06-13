package com.devpaul.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.profile.ui.ProfileScreen

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Profile.route) {
        ProfileScreen(navController)
    }
}