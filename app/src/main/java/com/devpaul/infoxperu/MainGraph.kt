package com.devpaul.infoxperu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devpaul.auth.authGraph
import com.devpaul.core_domain.Screen
import com.devpaul.home.homeGraph
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.news.newsGraph

@Composable
fun MainGraph(
    navController: NavHostController,
    appNavigator: AppNavigator
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        authGraph(navController, appNavigator)
        homeGraph(navController, appNavigator)
        newsGraph(navController, appNavigator)
    }
}