package com.devpaul.auth.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.ui.login.components.LoginForm
import com.devpaul.core_data.Screen
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val viewModel: LoginViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onUiEvent = { event, showSnackBar ->
            when (event) {
                is LoginUiEvent.UserLogged -> {
                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }

                }

                is LoginUiEvent.RecoveryPasswordSuccess -> {
                    showSnackBar(event.message)
                }
            }
        },
    ) { _, uiState, onIntent, showSnackBar, _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            LoginContent(
                navHostController = navHostController,
                onIntent = onIntent,
                showSnackBar = showSnackBar,
                uiState = uiState,
            )
        }
    }
}

@Composable
fun LoginContent(
    navHostController: NavHostController,
    onIntent: (LoginUiIntent) -> Unit,
    showSnackBar: (String) -> Unit,
    uiState: LoginUIState,
) {
    LoginForm(
        navHostController = navHostController,
        onLogin = { email, password, rememberMe ->
            onIntent(
                LoginUiIntent.Login(
                    email = email.trim(),
                    password = password.trim(),
                    rememberMe = rememberMe,
                )
            )
        },
        onForgotPassword = { email ->
            onIntent(LoginUiIntent.ResetPassword(email.trim()))
        },
        showSnackBar = showSnackBar,
        onIntent = onIntent,
        uiState = uiState,
    )
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(
        navHostController = rememberNavController(),
        onIntent = { },
        showSnackBar = { },
        uiState = LoginUIState()
    )
}