package com.devpaul.auth.domain.repository

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.core_data.DefaultOutput

interface AuthRepository {
    suspend fun login(requestLogin: LoginRequest): DefaultOutput<Login>
}