package com.devpaul.auth.ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.devpaul.auth.ui.register.components.RegisterForm
import com.devpaul.core_domain.Screen
import com.devpaul.util.screen.BaseScreen
import com.devpaul.util.screen.ShowDialogSuccessRegister
import com.devpaul.util.ui.extension.ScreenLoading
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
) {

    val viewModel: RegisterViewModel = koinViewModel()

    val uiEvent by viewModel.uiEvent.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    BaseScreen { _, showSnackBar ->
        LaunchedEffect(uiEvent) {
            when (uiEvent) {
                is RegisterUiEvent.RegisterSuccess -> {
                    showDialog = true
                }

                is RegisterUiEvent.RegisterError -> {
                    showSnackBar((uiEvent as RegisterUiEvent.RegisterError).error)
                }

                else -> Unit
            }

            viewModel.resetUiEvent()
        }

        if (showDialog) {
            ShowDialogSuccessRegister {
                showDialog = false
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                ScreenLoading()
            }

            RegisterForm(
                navController = navController,
                onRegister = { name, lastName, email, password ->
                    viewModel.executeUiIntent(
                        RegisterUiIntent.Register(
                            name,
                            lastName,
                            email,
                            password
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