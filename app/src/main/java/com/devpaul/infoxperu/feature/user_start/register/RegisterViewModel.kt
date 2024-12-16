package com.devpaul.infoxperu.feature.user_start.register

import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : StatelessViewModel<RegisterUiEvent, RegisterUiIntent>() {

    override suspend fun handleIntent(intent: RegisterUiIntent) {
        when (intent) {
            is RegisterUiIntent.Register -> register(
                intent.name,
                intent.lastname,
                intent.email,
                intent.password
            )
        }
    }

    fun register(
        name: String,
        lastName: String,
        email: String,
        password: String,
    ) {
        setLoading(true)
        executeInScope(
            block = {
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
            },
            onError = { error ->
                setUiEvent(RegisterUiEvent.RegisterError("Error en el registro: ${error.message}"))
            },
            onComplete = {
                setLoading(false)
            }
        )
    }
}
