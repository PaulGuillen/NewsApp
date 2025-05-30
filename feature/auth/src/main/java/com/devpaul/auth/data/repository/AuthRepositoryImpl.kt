package com.devpaul.auth.data.repository

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.dto.register.RegisterRequest
import com.devpaul.auth.data.datasource.remote.AuthServiceDS
import com.devpaul.auth.domain.entity.Login
import com.devpaul.auth.domain.entity.Register
import com.devpaul.auth.domain.repository.AuthRepository
import com.devpaul.core_data.DefaultOutput
import org.koin.core.annotation.Factory

@Factory([AuthRepository::class])
class AuthRepositoryImpl(
    private val serviceDS: AuthServiceDS
) : AuthRepository {

    override suspend fun login(requestLogin: LoginRequest): DefaultOutput<Login> {
        val request = LoginRequest(
            email = requestLogin.email,
            type = requestLogin.type,
            googleToken = requestLogin.googleToken,
        )
        return serviceDS.loginService(request)
    }

    override suspend fun register(requestRegister: RegisterRequest): DefaultOutput<Register> {
        val request = RegisterRequest(
            name = requestRegister.name,
            lastName = requestRegister.lastName,
            email = requestRegister.email,
            password = requestRegister.password,
        )
        return serviceDS.registerService(request)
    }

}