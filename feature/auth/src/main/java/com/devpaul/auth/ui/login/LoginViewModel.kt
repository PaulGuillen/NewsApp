package com.devpaul.auth.ui.login

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.usecase.LoginUC
import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.util.Constant.USER_UID_KEY
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import kotlinx.coroutines.delay
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val loginUC: LoginUC,
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
            is LoginUiIntent.Login -> login(email = intent.email)
            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(email = intent.email)
            is LoginUiIntent.CheckUserLoggedIn -> launchIO { checkUserLoggedIn() }
        }
    }

    private suspend fun login(email: String) {
        val requestLogin =
            LoginRequest(
                email = email,
                type = "classic",
                googleToken = ""
            )
        updateUiStateOnMain { it.copy(loginRequest = requestLogin) }
        updateUiStateOnMain { it.copy(login = ResultState.Loading) }
        val result = loginUC(LoginUC.Params(requestLogin))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is LoginUC.Success.LoginSuccess -> {
                        dataStoreUseCase.setValue(LOG_IN_KEY, true)
                        dataStoreUseCase.setValue(USER_UID_KEY, it.login.uid)
                        LoginUiEvent.UserLogged.send()
                    }
                }
            }
            .onFailure<LoginUC.Failure> {
                when (it) {
                    is LoginUC.Failure.LoginError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                login = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message ?: "Unknown error"
                                )
                            )
                        }
                    }
                }
            }
    }

    private fun sendPasswordResetEmail(email: String) {

    }

    private suspend fun checkUserLoggedIn() {
        val isLoggedIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) == true
        updateUiStateOnMain { it.copy(login = ResultState.Loading) }
        delay(Constant.LOGIN_DELAY)
        updateUiStateOnMain { it.copy(login = null) }
        if (isLoggedIn) {
            LoginUiEvent.UserLogged.send()
        }
    }
}