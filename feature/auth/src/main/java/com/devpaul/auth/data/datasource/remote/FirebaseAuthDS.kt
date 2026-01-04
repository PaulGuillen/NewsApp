package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.mapper.toDomain
import com.devpaul.auth.domain.entity.Auth
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class FirebaseAuthDS(
    private val auth: FirebaseAuth
) {

    suspend fun login(
        email: String,
        password: String,
    ): Auth {
        val result = auth
            .signInWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("Firebase user is null")

        return user.toDomain()
    }

    suspend fun register(
        email: String,
        password: String,
    ): Auth {
        val result = auth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("Firebase user is null")

        return user.toDomain()
    }

    suspend fun recoverPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}