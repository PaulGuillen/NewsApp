package com.devpaul.auth.ui.login

import com.devpaul.auth.domain.usecase.LoginUC
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val auth: FirebaseAuth,
    private val dataStoreUseCase: DataStoreUseCase,
    private val loginUC : LoginUC,
) : StatefulViewModel<LoginUIState, LoginUiIntent, LoginUiEvent>(
    defaultUIState = {
        LoginUIState()
    }
) {
    override suspend fun onUiIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.Login -> login(email = intent.email, password = intent.password)
            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(email = intent.email)
            is LoginUiIntent.CheckUserLoggedIn -> checkUserLoggedIn()
        }
    }

    private fun login(email: String, password: String) {
        // Activar loading
        setUiState(uiState.value.copy(isLoading = true))

    }

    private fun sendPasswordResetEmail(email: String) {

    }

    private fun checkUserLoggedIn() {
        setUiState(uiState.value.copy(isLoading = true))

    }
}