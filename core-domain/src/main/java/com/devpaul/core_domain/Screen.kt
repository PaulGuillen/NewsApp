package com.devpaul.core_domain

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}