package com.devpaul.auth.data.repository

import com.devpaul.auth.data.datasource.remote.FirebaseAuthDS
import com.devpaul.auth.domain.entity.Auth
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.domain.repository.AuthRepository
import org.koin.core.annotation.Factory

@Factory([AuthRepository::class])
class AuthRepositoryImpl(
    private val firebaseAuthDS: FirebaseAuthDS
) : AuthRepository {

    override suspend fun login(
        email: String,
        password: String,
    ): Auth = firebaseAuthDS.login(email, password)

    override suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String,
    ): Register = firebaseAuthDS.register(
        name = name,
        lastname = lastname,
        email = email,
        password = password,
    )

    override suspend fun recoverPassword(
        email: String,
    ) {
        firebaseAuthDS.recoverPassword(email)
    }
}