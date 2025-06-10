package com.devpaul.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.news.ui.news.NewsScreen
import com.devpaul.news.ui.news_detail.NewsDetailsScreen

fun NavGraphBuilder.newsGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.News.route) {
        NewsScreen(
            navController = navController,
            appNavigator = appNavigator,
        )
    }

    composable(
        route = Screen.NewsDetail.route,
        arguments = listOf(
            navArgument("newsType") { type = NavType.StringType },
            navArgument("country") { type = NavType.StringType }
        )
    ) { backStackEntry ->
        NewsDetailsScreen(
            navController = navController,
            newsType = backStackEntry.arguments?.getString("newsType"),
            country = backStackEntry.arguments?.getString("country")
        )
    }

}