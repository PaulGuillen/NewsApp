package com.devpaul.auth.domain.repository

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.dto.register.RegisterRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.auth.domain.entity.Register
import com.devpaul.core_data.DefaultOutput

interface AuthRepository {
    suspend fun login(requestLogin: LoginRequest): DefaultOutput<Login>
    suspend fun register(requestRegister: RegisterRequest): DefaultOutput<Register>
}