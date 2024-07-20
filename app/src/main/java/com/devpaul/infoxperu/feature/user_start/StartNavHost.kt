package com.devpaul.infoxperu.feature.user_start

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devpaul.infoxperu.feature.user_start.login.LoginScreen
import com.devpaul.infoxperu.feature.user_start.login.LoginViewModel
import com.devpaul.infoxperu.feature.user_start.register.RegisterScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Support : Screen("support")
}

@Composable
fun StartNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        // Agregar otras composables aquí
    }
}