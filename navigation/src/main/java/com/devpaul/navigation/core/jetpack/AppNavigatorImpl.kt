package com.devpaul.navigation.core.jetpack

import androidx.navigation.NavHostController
import com.devpaul.core_domain.Screen
import org.koin.core.annotation.Factory

@Factory
class AppNavigatorImpl(
    private val navController: NavHostController
) : AppNavigator {
    override fun navigateTo(screen: Screen) {
        navController.navigate(screen.route)
    }

    override fun popBack() {
        navController.popBackStack()
    }
}