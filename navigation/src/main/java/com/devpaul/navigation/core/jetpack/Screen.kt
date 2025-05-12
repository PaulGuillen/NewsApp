package com.devpaul.navigation.core.jetpack

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    data class Detail(val id: String) : Screen("detail/$id") {
        companion object {
            const val base = "detail/{id}"
        }
    }
}