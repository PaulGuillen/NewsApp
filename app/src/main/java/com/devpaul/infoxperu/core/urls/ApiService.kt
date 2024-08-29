package com.devpaul.infoxperu.core.urls

import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.NewsPeruResponse
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.UITResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("rest/noticias.json")
    suspend fun newsFromPeru(): Response<MutableList<NewsPeruResponse>>

    @GET("rest/cotizaciondolar.json")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("rest/uitperu.json")
    suspend fun dataUIT(): Response<UITResponse>

    @GET("v2/doc/doc")
    fun deltaProject(
        @Query("query") query: String,
        @Query("mode") mode: String,
        @Query("format") format: String
    ): Call<GDELProject>

    @GET("v2/top-headlines?country=ar&apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    suspend fun getDataArgentina(): Response<NewsResponse>

    @GET("v2/top-headlines?country=co&apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    suspend fun getDataColombia(): Response<NewsResponse>

    @GET("v2/top-headlines?country=cu&apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    suspend fun getDataCuba(): Response<NewsResponse>

    @GET("v2/top-headlines?country=mx&apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    suspend fun getDataMexico(): Response<NewsResponse>

    @GET("v2/top-headlines?country=ve&apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    suspend fun getDataVenezuela(): Response<NewsResponse>

}