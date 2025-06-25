package com.devpaul.auth.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.ui.login.components.LoginForm
import com.devpaul.core_data.Screen
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(navHostController: NavHostController) {
    val viewModel: LoginViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
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
        onLogin = { email, password ->
            onIntent(LoginUiIntent.Login(email.trim(), password.trim()))
        },
        onForgotPassword = { email ->
            onIntent(LoginUiIntent.ResetPassword(email.trim()))
        },
        showSnackBar = showSnackBar,
        uiState = uiState.login,
        onSuccess = {
            LaunchedEffect(Unit) {
                navHostController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
            }
        },
        onError = {
            val request = uiState.loginRequest
            onIntent(
                LoginUiIntent.Login(
                    email = request?.email ?: "",
                    password = ""
                )
            )
        }
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