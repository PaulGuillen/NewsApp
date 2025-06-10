package com.devpaul.navigation.core.jetpack

import androidx.navigation.NavHostController
import com.devpaul.core_data.Screen
import org.koin.core.annotation.Factory

@Factory
class AppNavigatorImpl(
    private val navController: NavHostController
) : AppNavigator {

    override fun navigateTo(
        screen: Screen,
        popUpTo: Screen?,
        inclusive: Boolean,
        data: Map<String, Any?>
    ) {
        val route = screen.route
        navController.navigate(route) {
            popUpTo?.let {
                popUpTo(it.route) { this.inclusive = inclusive }
            }
        }

        data.forEach { (key, value) ->
            navController.currentBackStackEntry?.savedStateHandle?.set(key, value)
        }
    }

    override fun popBack() {
        navController.popBackStack()
    }
}