package com.devpaul.infoxperu.feature.user_start.login.data.datasource.ds

import com.devpaul.infoxperu.core.urls.ApiService
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.user_start.login.data.datasource.mapper.LoginMapper
import com.devpaul.infoxperu.feature.user_start.login.domain.entity.LoginE
import javax.inject.Inject

class LoginServiceDS @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun loginService(request: RequestLogin): LoginE {
        val response = apiService.login(request)

        if (response.isSuccessful) {
            val responseLogin = response.body() ?: throw Exception(ERROR_FETCHING_DATA)

            return LoginMapper().mapResponseToEntity(responseLogin)
        } else {
            val errorMessage = response.errorBody()?.string() ?: ERROR_UNKNOWN
            throw ApiException(response.code(), errorMessage)
        }
    }

    companion object {
        const val ERROR_FETCHING_DATA = "Error fetching data: response body is null"
        const val ERROR_UNKNOWN = "Unknown error"
    }
}