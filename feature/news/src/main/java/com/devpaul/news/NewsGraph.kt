package com.devpaul.news

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.devpaul.news.ui.news.NewsScreen

fun NavGraphBuilder.newsGraph(
    navController: NavHostController,
) {
    composable(Screen.News.route) {
        NewsScreen(
            navController = navController,
        )
    }
}