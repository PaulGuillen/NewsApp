package com.devpaul.auth.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_data.Screen
import com.devpaul.shared.domain.handleDefaultErrors
import com.devpaul.shared.ui.components.organisms.BaseScreenWithState
import com.devpaul.shared.ui.components.organisms.ShowDialogSuccessRegister
import com.devpaul.shared.ui.components.atoms.base.ScreenLoading
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
                showSnackBar = { message ->
                    showSnackBar(message)
                }
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
            Timber.d("Error: ${event.error}")
        }
    }
}