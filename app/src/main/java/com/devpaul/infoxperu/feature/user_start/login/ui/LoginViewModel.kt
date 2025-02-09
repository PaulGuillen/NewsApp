package com.devpaul.infoxperu.feature.user_start.login.ui

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.user_start.login.domain.usecase.LoginUseCase
import com.devpaul.infoxperu.feature.util.Constant
import com.devpaul.infoxperu.feature.util.Constant.LOG_IN_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStoreUseCase: DataStoreUseCase,
    private val loginUseCase: LoginUseCase,
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
                val requestLogin = RequestLogin(
                    email = email,
                    type = "classic",
                    googleToken = ""
                )
                val result = loginUseCase(LoginUseCase.Params(requestLogin))
                if (result is ResultState.Success) {
                    dataStoreUseCase.setValue(LOG_IN_KEY, true)
                    Timber.d("DataStore: $LOG_IN_KEY = true")
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
                auth.sendPasswordResetEmail(email).await()
                setUiEvent(LoginUiEvent.RecoveryPasswordSuccess(message = Constant.PASSWORD_RECOVERY_SUCCESS))
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
                Timber.d("DataStore: $LOG_IN_KEY = $logIn")
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