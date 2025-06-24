package com.devpaul.auth.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.theme.BrickRed
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
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            BrickRed,
                            BrickRed,
                        )
                    )
                )
        )

        RegisterForm(
            navHostController = navHostController,
            onRegister = { name, lastName, phone, birthdate, email, password ->
                onIntent(
                    RegisterUiIntent.Register(
                        name = name,
                        lastname = lastName,
                        phone = phone,
                        birthdate = birthdate,
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
            onError = {
                val request = uiState.registerRequest
                onIntent(
                    RegisterUiIntent.Register(
                        name = request?.name ?: "",
                        lastname = request?.lastName ?: "",
                        phone = request?.phone ?: "",
                        birthdate = request?.birthdate ?: "",
                        email = request?.email ?: "",
                        password = request?.password ?: "",
                    )
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterContentPreview() {
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