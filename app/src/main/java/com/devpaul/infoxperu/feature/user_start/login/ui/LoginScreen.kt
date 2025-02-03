package com.devpaul.infoxperu.feature.user_start.login.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.domain.screen.BaseScreen
import com.devpaul.infoxperu.domain.ui.utils.ScreenLoading
import com.devpaul.infoxperu.feature.user_start.Screen
import com.devpaul.infoxperu.feature.user_start.login.ui.organisms.LoginForm

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {
    val uiEvent by viewModel.uiEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BaseScreen { _, showSnackBar ->
        LaunchedEffect(Unit) {
            viewModel.executeUiIntent(LoginUiIntent.CheckUserLoggedIn)
        }

        LaunchedEffect(uiEvent) {
            when (uiEvent) {
                is LoginUiEvent.LoginSuccess -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }

                is LoginUiEvent.LoginError -> showSnackBar((uiEvent as LoginUiEvent.LoginError).error)
                is LoginUiEvent.RecoveryPasswordSuccess -> showSnackBar((uiEvent as LoginUiEvent.RecoveryPasswordSuccess).message)
                is LoginUiEvent.RecoveryPasswordError -> showSnackBar((uiEvent as LoginUiEvent.RecoveryPasswordError).error)
                else -> Unit
            }
            viewModel.resetUiEvent()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                ScreenLoading()
            }

            LoginForm(
                onLoginClick = { email, password ->
                    viewModel.executeUiIntent(LoginUiIntent.Login(email.trim(), password.trim()))
                },
                onForgotPasswordClick = { email ->
                    viewModel.executeUiIntent(LoginUiIntent.ResetPassword(email.trim()))
                },
                onRegisterClick = {
                    navController.navigate(Screen.Register.route)
                },
                showSnackBar = { message ->
                    showSnackBar(message)
                },
            )
        }
    }
}