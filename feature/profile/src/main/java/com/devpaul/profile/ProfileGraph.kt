package com.devpaul.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.profile.ui.about.AboutScreen
import com.devpaul.profile.ui.profile.ProfileScreen
import com.devpaul.profile.ui.suggestions.SuggestionsScreen
import com.devpaul.profile.ui.update.UpdateScreen

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
) {
    composable(route = Screen.Profile.route) {
        ProfileScreen(navController = navController)
    }

    composable(route = Screen.ProfileUpdate.route) {
        UpdateScreen(navController = navController)
    }

    composable(route = Screen.Suggestions.route) {
        SuggestionsScreen(navController = navController)
    }

    composable(route = Screen.About.route) {
        AboutScreen(navController = navController)
    }
}