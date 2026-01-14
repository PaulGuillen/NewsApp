package com.devpaul.home.data.datasource.remote

import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeApi {

    @GET("api/rest/cotizaciondolar.json")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("api/rest/uitperu.json")
    suspend fun uit(): Response<UITResponse>
}