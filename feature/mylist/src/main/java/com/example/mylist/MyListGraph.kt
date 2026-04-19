package com.example.mylist

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.devpaul.core_data.Screen
import com.example.mylist.ui.MyListScreen

fun NavGraphBuilder.myListGraph(
    navController: NavHostController,
) {
    composable(Screen.MyList.route) {
        MyListScreen(navHostController = navController)
    }
}