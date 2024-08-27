package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
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
                val result = auth.signInWithEmailAndPassword(email, password).await() // Utiliza await para esperar asincrónicamente
                setLoading(false)
                if (result.user != null) {
                    dataStoreUseCase.setValue("logIn", true)
                    Timber.d("logIn - LoginViewModelOne: ${dataStoreUseCase.getBoolean("logIn")}")
                    setUiEvent(LoginUiEvent.LoginSuccess("Inicio de sesión exitoso"))
                } else {
                    setUiEvent(LoginUiEvent.LoginError("Inicio de sesión fallido"))
                }
            } catch (e: Exception) {
                setLoading(false)
                setUiEvent(LoginUiEvent.LoginError("Error en el inicio de sesión: ${e.message}"))
            }
        }
    }
    
    fun sendPasswordResetEmail(email: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        setLoading(false)
                        if (task.isSuccessful) {
                            setUiEvent(LoginUiEvent.RecoveryPasswordSuccess("Correo de recuperación enviado"))
                        } else {
                            setUiEvent(LoginUiEvent.RecoveryPasswordError("Error al enviar el correo de recuperación"))
                        }
                    }
                    .addOnFailureListener {
                        setLoading(false)
                        setUiEvent(LoginUiEvent.RecoveryPasswordError("Error al enviar el correo de recuperación: ${it.message}"))
                    }
            } catch (e: Exception) {
                setLoading(false)
                setUiEvent(LoginUiEvent.RecoveryPasswordError("Error al enviar el correo de recuperación: ${e.message}"))
            }
        }
    }

    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            val logIn = dataStoreUseCase.getBoolean("logIn") ?: false
            Timber.d("logIn: $logIn")
            if (logIn) {
                setLoading(true)
                delay(3000)  // Simular una carga o espera de proceso
                if (auth.currentUser != null) {
                    setUiEvent(LoginUiEvent.LoginSuccess("Usuario ya autenticado"))
                }
                setLoading(false)
            } else {
                Timber.d("No loading shown as user not logged in.")
            }
        }
    }

}
