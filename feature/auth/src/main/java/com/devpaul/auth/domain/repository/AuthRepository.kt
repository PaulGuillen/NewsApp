package com.devpaul.auth.domain.repository

import com.devpaul.auth.domain.entity.Auth
import com.devpaul.auth.domain.entity.Register

interface AuthRepository {

    suspend fun login(email: String, password: String): Auth
    suspend fun register(
        name: String,
        lastname: String,
        email: String,
        password: String
    ): Register

    suspend fun recoverPassword(email: String)
}