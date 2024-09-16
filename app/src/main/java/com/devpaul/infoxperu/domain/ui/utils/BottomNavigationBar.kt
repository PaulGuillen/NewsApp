package com.devpaul.infoxperu.domain.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.ui.theme.Black
import com.devpaul.infoxperu.ui.theme.BrickRed
import com.devpaul.infoxperu.ui.theme.White

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.White,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.bottom_nav_home)
                )
            },
            label = { Text(stringResource(id = R.string.bottom_nav_home)) },
            selected = navController.currentBackStackEntry?.destination?.route == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = White,
                selectedTextColor = Black,
                indicatorColor = BrickRed,
                unselectedIconColor = Black,
                unselectedTextColor = Black
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.bottom_nav_news)
                )
            },
            label = { Text(stringResource(id = R.string.bottom_nav_news)) },
            selected = navController.currentBackStackEntry?.destination?.route == "news",
            onClick = {
                navController.navigate("news") {
                    popUpTo("news") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = White,
                selectedTextColor = Black,
                indicatorColor = BrickRed,
                unselectedIconColor = Black,
                unselectedTextColor = Black
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.bottom_nav_services)
                )
            },
            label = { Text(stringResource(id = R.string.bottom_nav_services)) },
            selected = navController.currentBackStackEntry?.destination?.route == "contacts",
            onClick = {
                navController.navigate("contacts") {
                    popUpTo("contacts") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = White,
                selectedTextColor = Black,
                indicatorColor = BrickRed,
                unselectedIconColor = Black,
                unselectedTextColor = Black
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.bottom_nav_districts)
                )
            },
            label = { Text(stringResource(id = R.string.bottom_nav_districts)) },
            selected = navController.currentBackStackEntry?.destination?.route == "districts",
            onClick = {
                navController.navigate("districts") {
                    popUpTo("districts") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = White,
                selectedTextColor = Black,
                indicatorColor = BrickRed,
                unselectedIconColor = Black,
                unselectedTextColor = Black
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}
