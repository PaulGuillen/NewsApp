package com.devpaul.home.data.datasource.remote

import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.GratitudeResponse
import com.devpaul.home.data.datasource.dto.response.SectionResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeApi {

    @GET("http://192.168.100.137:3000/home/dollarQuote")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("http://192.168.100.137:3000/home/uit")
    suspend fun uit(): Response<UITResponse>

    @GET("http://192.168.100.137:3000/home/section")
    suspend fun sections(): Response<SectionResponse>

    @GET("http://192.168.100.137:3000/home/gratitude")
    suspend fun gratitude(): Response<GratitudeResponse>
}