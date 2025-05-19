package com.devpaul.auth.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devpaul.auth.ui.login.components.LoginForm
import com.devpaul.core_domain.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.ui.extension.ScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(appNavigator: AppNavigator) {
    val viewModel: LoginViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        onInit = { LoginUiIntent.CheckUserLoggedIn },
        onUiEvent = { event, showSnackBar ->
            when (event) {
                is LoginUiEvent.LoginSuccess -> appNavigator.navigateTo(
                    screen = Screen.Home,
                    popUpTo = Screen.Login,
                    inclusive = true
                )

                is LoginUiEvent.LoginError -> showSnackBar(event.error)
                is LoginUiEvent.RecoveryPasswordSuccess -> showSnackBar(event.message)
                is LoginUiEvent.RecoveryPasswordError -> showSnackBar(event.error)
                else -> Unit
            }
        }
    ) { _, uiState, onIntent, showSnackBar ->
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                ScreenLoading()
            } else {
                LoginForm(
                    onLoginClick = { email, password ->
                        onIntent(LoginUiIntent.Login(email.trim(), password.trim()))
                    },
                    onForgotPasswordClick = { email ->
                        onIntent(LoginUiIntent.ResetPassword(email.trim()))
                    },
                    onRegisterClick = {
                        appNavigator.navigateTo(Screen.Register)
                    },
                    showSnackBar = { message ->
                        showSnackBar(message)
                    },
                )
            }
        }
    }
}