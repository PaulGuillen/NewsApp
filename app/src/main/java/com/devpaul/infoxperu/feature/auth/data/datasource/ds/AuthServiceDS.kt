package com.devpaul.infoxperu.feature.auth.data.datasource.ds

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.data.datasource.mapper.LoginMapper
import com.devpaul.infoxperu.feature.auth.data.datasource.mapper.RecoveryPasswordMapper
import com.devpaul.infoxperu.feature.auth.data.datasource.mapper.RegisterMapper
import com.devpaul.infoxperu.feature.auth.domain.entity.LoginE
import com.devpaul.infoxperu.feature.auth.domain.entity.RecoveryPasswordE
import com.devpaul.infoxperu.feature.auth.domain.entity.RegisterE
import javax.inject.Inject

class AuthServiceDS @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(request: RequestLogin): LoginE {

        try {
            val response = apiService.login(request)
            if (response.isSuccessful) {
                val responseLogin = response.body() ?: throw Exception(ERROR_FETCHING_DATA)
                return LoginMapper().mapResponseToEntity(responseLogin)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw ApiException(500, e.message ?: ERROR_UNKNOWN)
        }
    }

    suspend fun register(request: RequestRegister): RegisterE {
        try {
            val response = apiService.register(request)
            if (response.isSuccessful) {
                val responseRegister = response.body() ?: throw Exception(ERROR_FETCHING_DATA)
                return RegisterMapper().mapResponseToEntity(responseRegister)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw ApiException(500, e.message ?: ERROR_UNKNOWN)
        }
    }

    suspend fun recoveryPassword(request: RequestRecoveryPassword): RecoveryPasswordE {
        try {
            val response = apiService.recoveryPassword(request)
            if (response.isSuccessful) {
                val responseRecoveryPassword =
                    response.body() ?: throw Exception(ERROR_FETCHING_DATA)
                return RecoveryPasswordMapper().mapResponseToEntity(responseRecoveryPassword)
            } else {
                val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
                throw ApiException(response.code(), errorMessage)
            }
        } catch (e: Exception) {
            throw ApiException(500, e.message ?: ERROR_UNKNOWN)
        }
    }

    companion object {
        const val ERROR_FETCHING_DATA = "Error fetching data: response body is null"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}