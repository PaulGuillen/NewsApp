package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.dto.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApi {

    @POST("http://192.168.100.13:3000/users/login")
    suspend fun login(
        @Body request: LoginRequest
    ) : Response<LoginResponse>
}