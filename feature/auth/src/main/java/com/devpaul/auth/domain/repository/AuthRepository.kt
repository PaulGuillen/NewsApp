package com.devpaul.auth.domain.repository

import com.devpaul.auth.domain.entity.Auth

interface AuthRepository {

    suspend fun login(email: String, password: String): Auth
    suspend fun register(email: String, password: String): Auth
    suspend fun recoverPassword(email: String)
}