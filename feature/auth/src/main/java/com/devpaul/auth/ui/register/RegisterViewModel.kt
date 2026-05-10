package com.devpaul.auth.ui.register

import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.domain.usecase.AuthUC
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.util.Constant.USER_UID_KEY
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RegisterViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val authUC: AuthUC,
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
        updateUiStateOnMain {
            it.copy(register = ResultState.Loading)
        }

        when (
            val result = authUC.register(
                name = name,
                lastname = lastName,
                email = email,
                password = password,
            )
        ) {
            is Output.Success -> {
                val register: Register = result.data

                dataStoreUseCase.setValue(USER_UID_KEY, register.uid)
                dataStoreUseCase.setValue(LOG_IN_KEY, true)

                updateUiStateOnMain {
                    it.copy(register = ResultState.Success(register))
                }
            }

            is Output.Failure -> {
                val message = mapRegisterErrorToMessage(result.error)

                updateUiStateOnMain {
                    it.copy(register = ResultState.Error(message))
                }

                RegisterUiEvent.RegisterError(message).send()
            }
        }
    }

    private fun mapRegisterErrorToMessage(error: AuthUC.Failure): String =
        when (error) {
            AuthUC.Failure.InvalidCredentials ->
                "Los datos ingresados no son válidos."

            AuthUC.Failure.EmailAlreadyInUse ->
                "El correo electrónico ya se encuentra en uso."

            AuthUC.Failure.InvalidUser ->
                "No se pudo crear la cuenta solicitada."

            AuthUC.Failure.Network ->
                "Ocurrió un error de conexión. Verifica tu acceso a internet."

            is AuthUC.Failure.Unknown ->
                "Ocurrió un error inesperado. Inténtalo nuevamente."
        }
}