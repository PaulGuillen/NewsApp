package com.devpaul.emergency.data.datasource.remote

import com.devpaul.emergency.data.datasource.dto.res.GeneralResponse
import com.devpaul.emergency.data.datasource.dto.res.SectionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface EmergencyApi {

    @GET("district/section")
    suspend fun section(): Response<SectionResponse>

    @GET("district/general")
    suspend fun general(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("district/civil_defense")
    suspend fun civilDefense(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("district/lima")
    suspend fun limaSecurity(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>

    @GET("district/province")
    suspend fun provincesSecurity(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GeneralResponse>
}