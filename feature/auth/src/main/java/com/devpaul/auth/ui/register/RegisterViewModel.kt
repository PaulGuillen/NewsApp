package com.devpaul.auth.ui.register

import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
) : StatefulViewModel<RegisterUiState, RegisterUiIntent, RegisterUiEvent>(
    defaultUIState = {
        RegisterUiState()
    }
) {

    override suspend fun onUiIntent(intent: RegisterUiIntent) {
        when (intent) {
            is RegisterUiIntent.Register -> launchIO {
                register(
                    name = intent.name,
                    lastName = intent.lastname,
                    email = intent.email,
                    password = intent.password,
                )
            }
        }
    }

    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ) {

    }

}