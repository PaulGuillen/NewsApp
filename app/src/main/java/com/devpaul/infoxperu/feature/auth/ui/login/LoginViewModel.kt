package com.devpaul.infoxperu.feature.auth.ui.login

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.domain.usecase.LoginUC
import com.devpaul.infoxperu.feature.auth.domain.usecase.RecoveryPasswordUC
import com.devpaul.infoxperu.feature.util.Constant
import com.devpaul.infoxperu.feature.util.Constant.LOG_IN_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase,
    private val loginUC: LoginUC,
    private val recoverPasswordUC: RecoveryPasswordUC,
) : StatelessViewModel<LoginUiEvent, LoginUiIntent>() {

    override fun handleIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.CheckUserLoggedIn -> checkUserLoggedIn()
            is LoginUiIntent.Login -> login(email = intent.email)
            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(email = intent.email)
        }
    }

    private fun login(email: String) {
        setLoading(true)
        executeInScope(
            block = {
                val requestLogin =
                    RequestLogin(
                        email = email,
                        type = "classic",
                        googleToken = ""
                    )
                val result = loginUC(LoginUC.Params(requestLogin))
                if (result is ResultState.Success) {
                    dataStoreUseCase.setValue(LOG_IN_KEY, true)
                    setUiEvent(LoginUiEvent.LoginSuccess(message = Constant.LOGIN_SUCCESS))
                } else {
                    setUiEvent(LoginUiEvent.LoginError(error = Constant.LOGIN_FAILURE))
                }
            },
            onError = { error ->
                setUiEvent(LoginUiEvent.LoginError(error = "${Constant.LOGIN_ERROR} ${error.message}"))
            },
            onComplete = {
                setLoading(false)
            }
        )
    }

    private fun sendPasswordResetEmail(email: String) {
        setLoading(true)
        executeInScope(
            block = {
                val requestRecoveryPassword =
                    RequestRecoveryPassword(
                        email = email,
                    )
                val result = recoverPasswordUC(RecoveryPasswordUC.Params(requestRecoveryPassword))
                if (result is ResultState.Success) {
                    setUiEvent(LoginUiEvent.RecoveryPasswordSuccess(message = Constant.PASSWORD_RECOVERY_SUCCESS))
                } else {
                    setUiEvent(LoginUiEvent.RecoveryPasswordError(error = Constant.PASSWORD_RECOVERY_ERROR))
                }
            },
            onError = { error ->
                setUiEvent(LoginUiEvent.RecoveryPasswordError(error = "${Constant.PASSWORD_RECOVERY_FAILURE} ${error.message}"))
            },
            onComplete = {
                setLoading(false)
            }
        )
    }

    private fun checkUserLoggedIn() {
        setLoading(true)
        executeInScope(
            block = {
                val logIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) ?: false
                if (logIn) {
                    delay(Constant.LOGIN_DELAY)
                    setUiEvent(LoginUiEvent.LoginSuccess(Constant.USER_ALREADY_LOGGED_IN))
                }
            },
            onComplete = {
                setLoading(false)
            }
        )
    }
}