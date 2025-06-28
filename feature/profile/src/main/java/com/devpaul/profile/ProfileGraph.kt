package com.devpaul.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.profile.ui.about.AboutScreen
import com.devpaul.profile.ui.profile.ProfileScreen
import com.devpaul.profile.ui.suggestions.SuggestionsScreen
import com.devpaul.profile.ui.update.UpdateScreen

fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.Profile.route) {
        ProfileScreen(navController = navController)
    }

    composable(
        route = Screen.ProfileUpdate.route,
        arguments = listOf(
            navArgument("profile_data") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        UpdateScreen(
            navController = navController,
            profileData = backStackEntry.arguments?.getString("profile_data")
        )
    }

    composable(Screen.Suggestions.route) {
        SuggestionsScreen(navController = navController)
    }

    composable(Screen.About.route) {
        AboutScreen(navController = navController)
    }
}