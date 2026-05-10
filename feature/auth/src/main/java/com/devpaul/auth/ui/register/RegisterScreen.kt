package com.devpaul.auth.ui.register

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(navHostController: NavHostController) {

    val viewModel: RegisterViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, showSnackBar, _ ->
        Box(modifier = Modifier.fillMaxSize()) {
            RegisterContent(
                navHostController = navHostController,
                onIntent = onIntent,
                showSnackBar = showSnackBar,
                uiState = uiState,
            )
        }
    }
}

@Composable
fun RegisterContent(
    navHostController: NavHostController,
    onIntent: (RegisterUiIntent) -> Unit,
    showSnackBar: (String) -> Unit,
    uiState: RegisterUiState,
) {
    RegisterForm(
        navHostController = navHostController,
        onRegister = { name, lastName, email, password ->
            onIntent(
                RegisterUiIntent.Register(
                    name = name,
                    lastname = lastName,
                    email = email,
                    password = password,
                )
            )
        },
        showSnackBar = { message -> showSnackBar(message) },
        registerState = uiState.register,
        onSuccess = {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        },
        onError = {}
    )
}

@Preview(
    name = "Register Screen - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Register Screen - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun RegisterContentPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        RegisterContent(
            navHostController = rememberNavController(),
            onIntent = { },
            showSnackBar = { },
            uiState = RegisterUiState(
                register = ResultState.Success(
                    Register(
                        status = 200,
                        message = "Registration successful",
                        uid = "12345",
                    )
                )
            ),
        )
    }
}
