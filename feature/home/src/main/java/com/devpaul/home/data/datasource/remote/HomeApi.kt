package com.devpaul.home.data.datasource.remote

import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.HolidayAlertResponse
import com.devpaul.home.data.datasource.dto.response.SeasonResponse
import com.devpaul.home.data.datasource.dto.response.SunatExchangeRateResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface HomeApi {

    @GET("api/rest/cotizaciondolar.json")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("api/rest/uitperu.json")
    suspend fun uit(): Response<UITResponse>

    @GET("api/rest/estaciones.json")
    suspend fun season(): Response<SeasonResponse>

    @GET("api/rest/esFeriado.json")
    suspend fun holidayAlert(): Response<HolidayAlertResponse>

    @GET("v1/tipo-cambio-sunat")
    suspend fun sunatExchangeRate(): Response<SunatExchangeRateResponse>
}