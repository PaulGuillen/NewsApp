package com.devpaul.infoxperu.feature.user_start

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devpaul.infoxperu.feature.home.district_view.DistrictScreen
import com.devpaul.infoxperu.feature.home.home_view.HomeScreen
import com.devpaul.infoxperu.feature.home.news_view.NewsScreen
import com.devpaul.infoxperu.feature.home.service_view.ServiceScreen
import com.devpaul.infoxperu.feature.user_start.login.LoginScreen
import com.devpaul.infoxperu.feature.user_start.register.RegisterScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object News : Screen("news")
    data object Services : Screen("services")
    data object Districts : Screen("districts")
}

@Composable
fun StartNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.News.route) {
            NewsScreen(navController = navController)
        }
        composable(Screen.Services.route) {
            ServiceScreen(navController = navController)
        }
        composable(Screen.Districts.route) {
            DistrictScreen(navController = navController)
        }
    }
}