package com.devpaul.auth.ui.login.components

import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.viewmodel.StatelessViewModel
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LoginViewModel(
    private val auth: FirebaseAuth,
    private val dataStoreUseCase: DataStoreUseCase,
) : StatelessViewModel<LoginUiEvent, LoginUiIntent>() {

    override fun handleIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.Login -> login(email = intent.email, password = intent.password)
            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(email = intent.email)
            is LoginUiIntent.CheckUserLoggedIn -> checkUserLoggedIn()
        }
    }

    private fun login(email: String, password: String) {
        setLoading(true)
        executeInScope(
            block = {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
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
                val logIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) == true
                if (logIn) {
                    delay(Constant.LOGIN_DELAY)
                    if (auth.currentUser != null) {
                        setUiEvent(LoginUiEvent.LoginSuccess(Constant.USER_ALREADY_LOGGED_IN))
                    }
                }
            },
            onComplete = {
                setLoading(false)
            }
        )
    }
}