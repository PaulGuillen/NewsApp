package com.devpaul.auth.ui.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_data.Screen
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.ShowDialogSuccessRegister
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun RegisterScreen(navHostController: NavHostController) {

    val viewModel: RegisterViewModel = koinViewModel()

    BaseScreenWithState(
        viewModel = viewModel,
        navController = navHostController,
        onUiEvent = { event, showSnackBar ->
            handleRegisterUiEvent(event, showSnackBar)
        },
        onDefaultError = { error, showSnackBar ->
            handleDefaultErrors(error, showSnackBar)
        }
    ) { _, uiState, onIntent, showSnackBar, _ ->

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                ScreenLoading()
            }

            if (uiState.showDialog) {
                ShowDialogSuccessRegister {
                    viewModel.setUiState(uiState.copy(showDialog = false))
                    navHostController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            RegisterContent(
                navHostController = navHostController,
                onIntent = onIntent,
                showSnackBar = showSnackBar
            )
        }
    }
}

private fun handleRegisterUiEvent(
    event: RegisterUiEvent,
    showSnackBar: (String) -> Unit,
) {
    when (event) {
        is RegisterUiEvent.RegisterError -> {
            showSnackBar(event.error)
        }
    }
}

@Composable
fun RegisterContent(
    navHostController: NavHostController,
    onIntent: (RegisterUiIntent) -> Unit,
    showSnackBar: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(bottomStart = 80.dp, bottomEnd = 80.dp))
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
            showSnackBar = { message -> showSnackBar(message) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterContentPreview() {
    RegisterContent(
        navHostController = rememberNavController(),
        onIntent = { },
        showSnackBar = { }
    )
}