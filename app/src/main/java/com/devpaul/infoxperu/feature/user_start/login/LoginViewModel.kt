package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.util.Constant.LOGIN_DELAY
import com.devpaul.infoxperu.feature.util.Constant.LOGIN_ERROR
import com.devpaul.infoxperu.feature.util.Constant.LOGIN_FAILURE
import com.devpaul.infoxperu.feature.util.Constant.LOGIN_SUCCESS
import com.devpaul.infoxperu.feature.util.Constant.LOG_IN_KEY
import com.devpaul.infoxperu.feature.util.Constant.PASSWORD_RECOVERY_ERROR
import com.devpaul.infoxperu.feature.util.Constant.PASSWORD_RECOVERY_FAILURE
import com.devpaul.infoxperu.feature.util.Constant.PASSWORD_RECOVERY_SUCCESS
import com.devpaul.infoxperu.feature.util.Constant.USER_ALREADY_LOGGED_IN
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
) : BaseViewModel<LoginUiEvent>() {

    init {
        checkUserLoggedIn()
    }

    fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                if (result.user != null) {
                    dataStoreUseCase.setValue(LOG_IN_KEY, true)
                    setUiEvent(LoginUiEvent.LoginSuccess(LOGIN_SUCCESS))
                } else {
                    setUiEvent(LoginUiEvent.LoginError(LOGIN_FAILURE))
                }
            } catch (e: Exception) {
                setUiEvent(LoginUiEvent.LoginError("$LOGIN_ERROR${e.message}"))
            } finally {
                setLoading(false)
            }
        }
    }

    fun sendPasswordResetEmail(email: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setUiEvent(
                                LoginUiEvent.RecoveryPasswordSuccess(
                                    PASSWORD_RECOVERY_SUCCESS
                                )
                            )
                        } else {
                            setUiEvent(LoginUiEvent.RecoveryPasswordError(PASSWORD_RECOVERY_FAILURE))
                        }
                    }
                    .addOnFailureListener {
                        setUiEvent(LoginUiEvent.RecoveryPasswordError("$PASSWORD_RECOVERY_ERROR${it.message}"))
                    }
            } catch (e: Exception) {
                setUiEvent(LoginUiEvent.RecoveryPasswordError("$PASSWORD_RECOVERY_ERROR${e.message}"))
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
                delay(LOGIN_DELAY)
                if (auth.currentUser != null) {
                    setUiEvent(LoginUiEvent.LoginSuccess(USER_ALREADY_LOGGED_IN))
                }
                setLoading(false)
            }
        }
    }
}
