package com.devpaul.auth.data.datasource.remote

import com.devpaul.auth.data.datasource.dto.login.LoginRequest
import com.devpaul.auth.data.datasource.dto.login.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

internal interface AuthApi {

    @POST("http://192.168.100.13:3000/users/login")
    suspend fun login(
        @Body login: LoginRequest
    ): Response<LoginResponse>

//    @POST("http://192.168.100.13:3000/users/register")
//    suspend fun register(
//        @Body register: RequestRegister
//    ): Response<ResponseRegister>
//
//    @POST("http://192.168.100.13:3000/users/recoveryPassword")
//    suspend fun recoveryPassword(
//        @Body recoveryPassword: RequestRecoveryPassword
//    ): Response<ResponseRecoveryPassword>
}