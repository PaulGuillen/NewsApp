package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.mapper.toDomain
import com.devpaul.auth.domain.entity.Auth
import com.devpaul.auth.domain.entity.Register
import com.devpaul.core_data.util.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Factory

@Factory
class FirebaseAuthDS(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
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
        name: String,
        lastname: String,
        email: String,
        password: String,
    ): Register {
        val result = auth
            .createUserWithEmailAndPassword(email, password)
            .await()

        val user = result.user
            ?: throw IllegalStateException("Firebase user is null")

        val uid = user.uid

        try {
            firestore.collection(Constant.USERS_COLLECTION)
                .document(uid)
                .set(
                    mapOf(
                        "uid" to uid,
                        "name" to name,
                        "lastname" to lastname,
                        "email" to email,
                        "password" to password,
                        "phone" to "",
                        "birthdate" to "",
                        "image" to ""
                    )
                )
                .await()
        } catch (ex: Exception) {
            user.delete().await()
            throw ex
        }

        return Register(
            status = 200,
            message = "Registration successful",
            uid = uid,
        )
    }

    suspend fun recoverPassword(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

}