package com.devpaul.auth.ui.login

import com.devpaul.auth.domain.entity.Auth
import com.devpaul.auth.domain.usecase.AuthUC
import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.util.Constant.USER_UID_KEY
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import kotlinx.coroutines.delay
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val authUC: AuthUC,
) : StatefulViewModel<LoginUIState, LoginUiIntent, LoginUiEvent>(
    defaultUIState = {
        LoginUIState()
    }
) {

    init {
        LoginUiIntent.CheckUserLoggedIn.execute()
    }

    override suspend fun onUiIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.Login -> login(
                email = intent.email,
                password = intent.password,
                rememberMe = intent.rememberMe,
            )

            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(email = intent.email)
            is LoginUiIntent.CheckUserLoggedIn -> launchIO { checkUserLoggedIn() }
            is LoginUiIntent.DismissDialog -> onDialogDismissed()
        }
    }

    private suspend fun login(email: String, password: String, rememberMe: Boolean) {

        updateUiStateOnMain {
            it.copy(loginStatus = ResultState.Loading)
        }

        when (val result = authUC.login(email = email, password = password)) {

            is Output.Success -> {

                val auth: Auth = result.data

                dataStoreUseCase.setValue(USER_UID_KEY, auth.uid)
                dataStoreUseCase.setValue(LOG_IN_KEY, rememberMe)

                updateUiStateOnMain {
                    it.copy(loginStatus = ResultState.Success(auth))
                }

                LoginUiEvent.UserLogged.send()
            }

            is Output.Failure -> {
                val message = mapAuthErrorToMessage(result.error)

                updateUiStateOnMain {
                    it.copy(loginStatus = ResultState.Error(message))
                }
            }
        }
    }

    private fun mapAuthErrorToMessage(error: AuthUC.Failure): String =
        when (error) {
            AuthUC.Failure.InvalidCredentials ->
                "El correo o la contraseña ingresados son incorrectos."

            AuthUC.Failure.EmailAlreadyInUse ->
                "El correo electrónico ya se encuentra en uso."

            AuthUC.Failure.Network ->
                "Ocurrió un error de conexión. Verifica tu acceso a internet."

            is AuthUC.Failure.Unknown ->
                "Ocurrió un error inesperado. Inténtalo nuevamente."
        }

    suspend fun onDialogDismissed() {
        updateUiStateOnMain {
            it.copy(loginStatus = ResultState.Idle)
        }
    }

    private suspend fun sendPasswordResetEmail(email: String) {

        updateUiStateOnMain {
            it.copy(recoveryPasswordStatus = ResultState.Loading)
        }

        when (val result = authUC.recoverPassword(email)) {
            is Output.Success -> {
                updateUiStateOnMain {
                    it.copy(recoveryPasswordStatus = ResultState.Idle)
                }

                LoginUiEvent.RecoveryPasswordSuccess(Constant.PASSWORD_RECOVERY_SUCCESS).send()
            }

            is Output.Failure -> {
                updateUiStateOnMain {
                    it.copy(recoveryPasswordStatus = ResultState.Idle)
                }

                LoginUiEvent.RecoveryPasswordError(
                    error = Constant.PASSWORD_RECOVERY_FAILURE + result.error.message
                ).send()

            }
        }
    }

    private suspend fun checkUserLoggedIn() {
        val isLoggedIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) == true
        if (isLoggedIn) {
            updateUiStateOnMain { it.copy(loginStatus = ResultState.Loading) }
            delay(Constant.LOGIN_DELAY)
            LoginUiEvent.UserLogged.send()
        }
        updateUiStateOnMain { it.copy(loginStatus = ResultState.Idle) }
    }
}