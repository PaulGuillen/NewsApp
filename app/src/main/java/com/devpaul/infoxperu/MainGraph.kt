package com.devpaul.infoxperu

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.devpaul.auth.authGraph
import com.devpaul.core_data.Screen
import com.devpaul.emergency.emergencyGraph
import com.devpaul.home.homeGraph
import com.devpaul.news.newsGraph
import com.devpaul.profile.profileGraph
import com.example.mylist.myListGraph

@Composable
fun MainGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        authGraph(navController)
        homeGraph(navController)
        newsGraph(navController)
        emergencyGraph(navController)
        myListGraph(navController)
        profileGraph(navController)
    }
}