package com.devpaul.infoxperu.feature.user_start

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devpaul.infoxperu.domain.ui.news_screen.AllNews
import com.devpaul.infoxperu.feature.home.home.ui.HomeScreen
import com.devpaul.infoxperu.feature.home.news.ui.NewsScreen
import com.devpaul.infoxperu.feature.home.profile.ProfileScreen
import com.devpaul.infoxperu.feature.home.services.ui.ContactScreen
import com.devpaul.infoxperu.feature.home.services.ui.district_screen.DistrictsScreen
import com.devpaul.infoxperu.feature.home.services.ui.management.DistrictManagement
import com.devpaul.infoxperu.feature.user_start.login.LoginScreen
import com.devpaul.infoxperu.feature.user_start.register.RegisterScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object News : Screen("news")
    data object Services : Screen("contacts")
    data object Districts : Screen("districts")
    data object Profile : Screen("profile")
    data object DistrictManagement : Screen("district_management/{districtSelected}") {
        fun createRoute(districtSelected: String) =
            "district_management/$districtSelected"
    }
    data object AllNews : Screen("all_news/{newsType}/{country}") {
        fun createRoute(newsType: String, country: String) = "all_news/$newsType/$country"
    }
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
            ContactScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.DistrictManagement.route,
            arguments = listOf(
                navArgument("districtSelected") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            DistrictManagement(
                navController = navController,
                districtSelected = backStackEntry.arguments?.getString("districtSelected")
            )
        }

        composable(Screen.Districts.route) {
            DistrictsScreen(navController = navController)
        }

        composable(
            route = Screen.AllNews.route,
            arguments = listOf(
                navArgument("newsType") { type = NavType.StringType },
                navArgument("country") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            AllNews(
                navController = navController,
                newsType = backStackEntry.arguments?.getString("newsType"),
                country = backStackEntry.arguments?.getString("country")
            )
        }

    }
}