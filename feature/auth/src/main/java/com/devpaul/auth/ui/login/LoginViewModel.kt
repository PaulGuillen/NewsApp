package com.devpaul.auth.ui.login

import androidx.lifecycle.viewModelScope
import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.usecase.LoginUC
import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class LoginViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val loginUC: LoginUC,
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

    private suspend fun login(email: String, password: String) {
        val requestLogin =
            LoginRequest(
                email = email,
                type = "classic",
                googleToken = ""
            )
        setUiState(uiState.copy(isLoading = true))
        val result = loginUC(LoginUC.Params(requestLogin))
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is LoginUC.Success.LoginSuccess -> {
                        dataStoreUseCase.setValue(LOG_IN_KEY, true)
                        LoginUiEvent.LoginSuccess(message = Constant.LOGIN_SUCCESS).send()
                    }
                }
            }
            .onFailure<LoginUC.Failure> {
                when (it) {
                    is LoginUC.Failure.LoginError -> {
                        LoginUiEvent.LoginError(error = Constant.LOGIN_ERROR).send()
                    }
                }
            }
            .also {
                setUiState(uiState.copy(isLoading = false))
            }
    }

    private fun sendPasswordResetEmail(email: String) {

    }

    private fun checkUserLoggedIn() {
        Timber.d("UserLogged ${dataStoreUseCase.getBoolean(LOG_IN_KEY)}")
        viewModelScope.launch {
            setUiState(uiState.copy(isLoading = true))
            delay(Constant.LOGIN_DELAY)
            val isLoggedIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) == true
            setUiState(uiState.copy(isLoading = false))
            if (isLoggedIn) {
                LoginUiEvent.LoginSuccess(Constant.LOGIN_SUCCESS).send()
            }
        }
    }

}