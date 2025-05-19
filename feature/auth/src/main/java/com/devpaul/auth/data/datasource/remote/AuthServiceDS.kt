package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.domain.entity.Login
import com.devpaul.core_data.DefaultOutput
import com.devpaul.core_data.safeApiCall
import com.devpaul.core_domain.entity.transform
import org.koin.core.annotation.Factory
import com.devpaul.auth.data.datasource.mapper.toDomain

@Factory
class AuthServiceDS (
    private val authServiceDS: AuthService,
) {

    suspend fun loginService(request: LoginRequest): DefaultOutput<Login> {
        return safeApiCall {
            authServiceDS.login(request)
        }.transform { it.toDomain() }
    }

}