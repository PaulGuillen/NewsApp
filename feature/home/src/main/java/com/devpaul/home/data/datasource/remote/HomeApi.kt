package com.devpaul.home.data.datasource.remote

import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.GratitudeResponse
import com.devpaul.home.data.datasource.dto.response.SectionResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

internal interface HomeApi {

    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
   // @GET("https://deperu.com/api/rest/cotizaciondolar.json")
    @GET("home/dollarQuote")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    //@GET("https://deperu.com/api/rest/uitperu.json")
    @GET("home/uit")
    suspend fun uit(): Response<UITResponse>

    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    @GET("home/section")
    suspend fun sections(): Response<SectionResponse>

    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    @GET("home/gratitude")
    suspend fun gratitude(): Response<GratitudeResponse>
}