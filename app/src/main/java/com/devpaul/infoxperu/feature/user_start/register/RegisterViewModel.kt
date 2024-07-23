package com.devpaul.infoxperu.feature.user_start.register

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : BaseViewModel<RegisterUiEvent>() {

    fun register(name: String, lastName: String, email: String, password: String, confirmPassword: String) {
        setLoading(true)
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val userId = result.user?.uid
                if (userId != null) {
                    val user = hashMapOf(
                        "uid" to userId,
                        "name" to name,
                        "lastName" to lastName,
                        "email" to email,
                        "password" to password
                    )
                    firestore.collection("users").document(userId).set(user).await()
                    setUiEvent(RegisterUiEvent.RegisterSuccess("Registro exitoso"))
                } else {
                    setUiEvent(RegisterUiEvent.RegisterError("Error al obtener el ID del usuario"))
                }
            } catch (e: Exception) {
                setUiEvent(RegisterUiEvent.RegisterError("Error en el registro: ${e.message}"))
            } finally {
                setLoading(false)
            }
        }
    }
}
