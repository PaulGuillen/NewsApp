package com.devpaul.auth.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_data.Screen
import com.devpaul.navigation.core.jetpack.AppNavigator
import com.devpaul.shared.ui.extension.handleDefaultErrors
import com.devpaul.shared.screen.BaseScreenWithState
import com.devpaul.shared.screen.ShowDialogSuccessRegister
import com.devpaul.shared.ui.extension.ScreenLoading
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun RegisterScreen(navHostController: NavHostController, appNavigator: AppNavigator) {

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
                    appNavigator.navigateTo(Screen.Home, popUpTo = Screen.Login, inclusive = true)
                }
            }

            RegisterForm(
                appNavigator = appNavigator,
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