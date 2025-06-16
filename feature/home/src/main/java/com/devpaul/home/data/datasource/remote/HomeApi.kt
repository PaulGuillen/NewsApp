package com.devpaul.home.data.datasource.remote

import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.GratitudeResponse
import com.devpaul.home.data.datasource.dto.response.SectionResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeApi {

    @GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    //@GET("http://192.168.100.137:3000/home/dollarQuote")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    //@GET("http://192.168.100.137:3000/home/uit")
    suspend fun uit(): Response<UITResponse>

    @GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    //@GET("http://192.168.100.137:3000/home/section")
    suspend fun sections(): Response<SectionResponse>

    @GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    //@GET("http://192.168.100.137:3000/home/gratitude")
    suspend fun gratitude(): Response<GratitudeResponse>
}