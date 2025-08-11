package com.devpaul.emergency.data.datasource.remote

import com.devpaul.emergency.data.datasource.dto.res.SectionResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface EmergencyApi {

    @GET("district/section")
    suspend fun section(): Response<SectionResponse>

}