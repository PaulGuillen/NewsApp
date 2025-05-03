package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.mapper.LoginMapper
import com.devpaul.auth.domain.entity.Login
import org.koin.core.annotation.Factory

@Factory
class AuthServiceDS (
    private val authServiceDS: AuthService,
) {

    suspend fun loginService(request: LoginRequest): Login {
        val response = authServiceDS.login(request)

        if (response.isSuccessful) {
            val responseLogin = response.body() ?: throw Exception(ERROR_FETCHING_DATA)
            return LoginMapper().mapResponseToEntity(responseLogin)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorMessage = errorBody ?: ERROR_UNKNOWN
            throw Exception(errorMessage)
        }
    }

    companion object {
        const val ERROR_FETCHING_DATA = "Error fetching data: response body is null"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}