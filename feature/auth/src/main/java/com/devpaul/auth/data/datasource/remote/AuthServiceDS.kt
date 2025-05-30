package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.dto.register.RegisterRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import org.koin.core.annotation.Factory
import com.devpaul.auth.data.datasource.mapper.toDomain
import com.devpaul.auth.domain.entity.Register

@Factory
class AuthServiceDS (
    private val authServiceDS: AuthService,
) {

    suspend fun loginService(request: LoginRequest): DefaultOutput<Login> {
        return safeApiCall {
            authServiceDS.login(request)
        }.transform { it.toDomain() }
    }

    suspend fun registerService(request: RegisterRequest): DefaultOutput<Register> {
        return safeApiCall {
            authServiceDS.register(request)
        }.transform { it.toDomain() }
    }

}