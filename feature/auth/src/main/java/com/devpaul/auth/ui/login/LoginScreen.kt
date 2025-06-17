package com.devpaul.auth.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devpaul.auth.ui.login.components.LoginForm
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.atoms.ScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navHostController: NavHostController, appNavigator: AppNavigator) {
    val viewModel: LoginViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onUiEvent = { event, showSnackBar ->
            handleLoginUiEvent(event, showSnackBar, appNavigator)
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, showSnackBar, _ ->

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
                    showSnackBar = showSnackBar
                )
            }
        }
    }
}

private fun handleLoginUiEvent(
    event: LoginUiEvent,
    showSnackBar: (String) -> Unit,
    navigator: AppNavigator
) {
    when (event) {
        is LoginUiEvent.LoginSuccess -> {
            navigator.navigateTo(
                screen = Screen.Home,
                popUpTo = Screen.Login,
                inclusive = true
            )
        }

        is LoginUiEvent.LoginError -> showSnackBar(event.error)
        is LoginUiEvent.RecoveryPasswordSuccess -> showSnackBar(event.message)
        is LoginUiEvent.RecoveryPasswordError -> showSnackBar(event.error)
    }
}