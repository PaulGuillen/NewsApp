package com.devpaul.shared.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlignVerticalBottom
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devpaul.core_data.Screen

@Composable
fun HomeBottomBar(navController: NavHostController) {

    val isDark = isSystemInDarkTheme()

    val containerColor = if (isDark)
        Color(0xFF0B0F19)
    else
        MaterialTheme.colorScheme.surface

    val selectedColor = if (isDark)
        Color(0xFF3B82F6)
    else
        MaterialTheme.colorScheme.primary

    val unselectedColor = if (isDark)
        Color(0xFF6B7280)
    else
        Color(0xFF9CA3AF)

    Box {
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )

        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            containerColor = containerColor,
            tonalElevation = 0.dp
        ) {

            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == Screen.Home.route,
                onClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                icon = { Icon(Icons.Default.Home, null) },
                label = { Text("Inicio") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == "news",
                onClick = {
                    navController.navigate("news") {
                        popUpTo("news") { inclusive = true }
                    }
                },
                icon = { Icon(Icons.Default.AlignVerticalBottom, null) },
                label = { Text("Mercado") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == "emergency",
                onClick = {
                    navController.navigate("emergency") {
                        popUpTo("emergency") { inclusive = true }
                    }
                },
                icon = { Icon(Icons.Default.DateRange, null) },
                label = { Text("Mi Lista") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = Color.Transparent
                )
            )

            NavigationBarItem(
                selected = navController.currentBackStackEntry?.destination?.route == "profile",
                onClick = {
                    navController.navigate("profile") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                icon = { Icon(Icons.Default.Person, null) },
                label = { Text("Perfil") },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedColor,
                    selectedTextColor = selectedColor,
                    unselectedIconColor = unselectedColor,
                    unselectedTextColor = unselectedColor,
                    indicatorColor = Color.Transparent
                )
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-22).dp),
            contentAlignment = Alignment.Center
        ) {

            if (isDark) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    selectedColor.copy(alpha = 0.20f),
                                    Color.Transparent
                                ),
                                radius = 140f
                            ),
                            shape = CircleShape
                        )
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                FloatingActionButton(
                    onClick = {},
                    containerColor = selectedColor,
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "S.O.S",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = selectedColor
                )
            }
        }
    }
}