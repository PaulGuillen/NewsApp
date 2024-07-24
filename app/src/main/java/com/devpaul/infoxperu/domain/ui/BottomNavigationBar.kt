package com.devpaul.infoxperu.domain.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.feature.user_start.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = navController.currentBackStackEntry?.destination?.route == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "Noticias") },
            label = { Text("Noticias") },
            selected = navController.currentBackStackEntry?.destination?.route == "news",
            onClick = {
                navController.navigate("news") {
                    popUpTo("news") { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "Servicios") },
            label = { Text("Servicios") },
            selected = navController.currentBackStackEntry?.destination?.route == "services",
            onClick = {
                navController.navigate("services") {
                    popUpTo("services") { inclusive = true }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "Distritos") },
            label = { Text("Distritos") },
            selected = navController.currentBackStackEntry?.destination?.route == "districts",
            onClick = {
                navController.navigate("districts") {
                    popUpTo("districts") { inclusive = true }
                }
            }
        )
    }
}