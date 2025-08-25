package com.devpaul.emergency.data.datasource.remote

import com.devpaul.emergency.data.datasource.dto.res.GeneralResponse
import com.devpaul.emergency.data.datasource.dto.res.SectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EmergencyApi {

    @GET("district/section")
    suspend fun section(): Response<SectionResponse>

    @GET("districts/general")
    suspend fun general(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("districts/civil_defense")
    suspend fun civilDefense(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("districts/lima")
    suspend fun limaSecurity(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("districts/province")
    suspend fun provincesSecurity(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>
}