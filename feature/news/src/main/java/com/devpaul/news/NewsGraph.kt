package com.devpaul.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_domain.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.news.ui.NewsScreen

fun NavGraphBuilder.newsGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    composable(Screen.News.route) {
        NewsScreen(navController)
    }
}