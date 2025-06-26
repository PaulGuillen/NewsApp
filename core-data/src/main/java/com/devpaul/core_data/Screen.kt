package com.devpaul.core_data

import android.net.Uri
import com.google.gson.Gson

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object News : Screen("news")
    data object NewsDetail : Screen(route = "all_news/{newsType}/{country}") {
        fun createRoute(newsType: String, country: Any): String {
            val gson = Gson()
            val encodedCountry = Uri.encode(gson.toJson(country))
            return "all_news/$newsType/$encodedCountry"
        }
    }
    data object Districts : Screen("districts")
    data object Profile : Screen("profile")
    data object ProfileUpdate : Screen("profile/update")
}