package com.devpaul.auth.data.repository

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.remote.AuthServiceDS
import com.devpaul.auth.domain.entity.Login
import com.devpaul.auth.domain.repository.BusinessRepository
import com.devpaul.core_data.DefaultOutput
import org.koin.core.annotation.Factory

@Factory([BusinessRepository::class])
class AuthRepositoryImpl(
    private val serviceDS: AuthServiceDS
) : BusinessRepository {
    override suspend fun login(requestLogin: LoginRequest): DefaultOutput<Login> {
        TODO("Not yet implemented")
    }

}