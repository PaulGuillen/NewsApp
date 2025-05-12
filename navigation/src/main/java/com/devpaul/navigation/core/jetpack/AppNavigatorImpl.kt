package com.devpaul.navigation.core.jetpack

import androidx.navigation.NavHostController

class AppNavigatorImpl(private val navController: NavHostController) : AppNavigator {
    override fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    override fun popBack() {
        navController.popBackStack()
    }
}