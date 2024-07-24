package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel<LoginUiEvent>() {

    init {
        checkUserLoggedIn()
    }

    fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        setLoading(false)
                        if (task.isSuccessful) {
                            setUiEvent(LoginUiEvent.LoginSuccess("Inicio de sesión exitoso"))
                        } else {
                            setUiEvent(LoginUiEvent.LoginError("Inicio de sesión fallido"))
                        }
                    }
                    .addOnFailureListener {
                        setLoading(false)
                        setUiEvent(LoginUiEvent.LoginError("Inicio de sesión fallido : ${it.message}"))
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
        setLoading(true)
        viewModelScope.launch {
            delay(3000)
            if (auth.currentUser != null) {
                setUiEvent(LoginUiEvent.LoginSuccess("Usuario ya autenticado"))
            }
            setLoading(false)
        }
    }

}
