package com.devpaul.navigation.core.jetpack

import androidx.navigation.NavHostController
import com.devpaul.core_domain.Screen
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Single

@Factory
class AppNavigatorImpl(
    private val navController: NavHostController
) : AppNavigator {

    override fun navigateTo(screen: Screen, popUpTo: Screen?, inclusive: Boolean) {
        navController.navigate(screen.route) {
            popUpTo?.let {
                popUpTo(it.route) { this.inclusive = inclusive }
            }
        }
    }

    override fun popBack() {
        navController.popBackStack()
    }
}