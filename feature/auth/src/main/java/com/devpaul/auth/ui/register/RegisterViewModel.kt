package com.devpaul.auth.ui.register

import com.devpaul.auth.data.datasource.dto.register.RegisterRequest
import com.devpaul.auth.domain.usecase.RegisterUC
import com.devpaul.core_data.util.Constant
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val registerUC: RegisterUC,
) : StatefulViewModel<RegisterUiState, RegisterUiIntent, RegisterUiEvent>(
    defaultUIState = {
        RegisterUiState()
    }
) {

    override suspend fun onUiIntent(intent: RegisterUiIntent) {
        when (intent) {
            is RegisterUiIntent.Register -> register(
                name = intent.name,
                lastName = intent.lastname,
                email = intent.email,
                password = intent.password,
            )
        }
    }

    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ) {

        val requestRegister = RegisterRequest(
            name = name,
            lastName = lastName,
            email = email,
            password = password
        )
        setUiState(uiState.copy(isLoading = true))
        val result = registerUC(RegisterUC.Params(requestRegister))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is RegisterUC.Success.RegisterSuccess -> {
                        setUiState(uiState.copy(showDialog = true))
                    }
                }
            }
            .onFailure<RegisterUC.Failure> {
                when (it) {
                    is RegisterUC.Failure.RegisterError -> {
                        RegisterUiEvent.RegisterError(error = Constant.REGISTER_ERROR).send()
                    }
                }
            }
            .also {
                setUiState(uiState.copy(isLoading = false))
            }
    }

}