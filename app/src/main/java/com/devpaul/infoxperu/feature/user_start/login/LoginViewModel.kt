package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.util.Constant
import com.devpaul.infoxperu.feature.util.Constant.LOG_IN_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val dataStoreUseCase: DataStoreUseCase
) : StatelessViewModel<LoginUiEvent, LoginUiIntent>() {

    override suspend fun handleIntent(intent: LoginUiIntent) {
        when (intent) {
            is LoginUiIntent.Login -> login(intent.email, intent.password)
            is LoginUiIntent.ResetPassword -> sendPasswordResetEmail(intent.email)
            is LoginUiIntent.CheckUserLoggedIn -> checkUserLoggedIn()
        }
    }

    private fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    dataStoreUseCase.setValue(LOG_IN_KEY, true)
                    setUiEvent(LoginUiEvent.LoginSuccess(Constant.LOGIN_SUCCESS))
                } else {
                    setUiEvent(LoginUiEvent.LoginError(Constant.LOGIN_FAILURE))
                }
            } catch (e: Exception) {
                setUiEvent(LoginUiEvent.LoginError("${Constant.LOGIN_ERROR} ${e.message}"))
            } finally {
                setLoading(false)
            }
        }
    }

    private fun sendPasswordResetEmail(email: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email).await()
                setUiEvent(LoginUiEvent.RecoveryPasswordSuccess(Constant.PASSWORD_RECOVERY_SUCCESS))
            } catch (e: Exception) {
                setUiEvent(LoginUiEvent.RecoveryPasswordError("${Constant.PASSWORD_RECOVERY_FAILURE} ${e.message}"))
            } finally {
                setLoading(false)
            }
        }
    }

    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            val logIn = dataStoreUseCase.getBoolean(LOG_IN_KEY) ?: false
            if (logIn) {
                setLoading(true)
                delay(Constant.LOGIN_DELAY)
                if (auth.currentUser != null) {
                    setUiEvent(LoginUiEvent.LoginSuccess(Constant.USER_ALREADY_LOGGED_IN))
                }
                setLoading(false)
            }
        }
    }
}
