package com.devpaul.auth.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.devpaul.auth.ui.login.components.organisms.LoginForm
import com.devpaul.core_domain.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.util.screen.BaseScreen
import com.devpaul.util.ui.ScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    appNavigator: AppNavigator,
) {
    val viewModel: LoginViewModel = koinViewModel()

    val uiEvent by viewModel.uiEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    BaseScreen { _, showSnackBar ->
        LaunchedEffect(Unit) {
            viewModel.executeUiIntent(LoginUiIntent.CheckUserLoggedIn)
        }

        LaunchedEffect(uiEvent) {
            when (uiEvent) {
                is LoginUiEvent.LoginSuccess -> {
                    appNavigator.navigateTo(
                        screen = Screen.Home,
                        popUpTo = Screen.Login,
                        inclusive = true,
                    )
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
                    appNavigator.navigateTo(screen = Screen.Register)
                },
                showSnackBar = { message ->
                    showSnackBar(message)
                },
            )
        }
    }
}