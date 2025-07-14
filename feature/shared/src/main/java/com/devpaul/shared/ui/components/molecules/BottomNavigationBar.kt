package com.devpaul.shared.ui.components.molecules

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        tonalElevation = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface,
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
                indicatorColor = BrickRed,
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
                indicatorColor = BrickRed,
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
                indicatorColor = BrickRed,
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = stringResource(id = R.string.bottom_nav_profile)
                )
            },
            label = { Text(stringResource(id = R.string.bottom_nav_profile)) },
            selected = navController.currentBackStackEntry?.destination?.route == "profile",
            onClick = {
                navController.navigate("profile") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = White,
                indicatorColor = BrickRed,
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