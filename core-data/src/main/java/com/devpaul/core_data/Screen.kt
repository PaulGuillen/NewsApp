package com.devpaul.core_data

import android.net.Uri
import com.google.gson.Gson

sealed class Screen(val route: String) {
    data object Login : Screen(route = "login")
    data object Register : Screen(route = "register")
    data object Home : Screen(route = "home")
    data object News : Screen(route = "news")
    data object NewsDetail : Screen(route = "all_news/{newsType}/{country}") {
        fun createRoute(newsType: String, country: Any): String {
            val gson = Gson()
            val encodedCountry = Uri.encode(gson.toJson(country))
            return "all_news/$newsType/$encodedCountry"
        }
    }

    data object Districts : Screen(route = "districts")
    data object Profile : Screen(route = "profile")
    data object ProfileUpdate : Screen(route = "profile/update")
    data object Suggestions : Screen(route = "profile/suggestions")
    data object About : Screen(route = "profile/about")
}