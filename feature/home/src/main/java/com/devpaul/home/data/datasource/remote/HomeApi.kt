package com.devpaul.home.data.datasource.remote

import com.devpaul.core_data.model.GDELProject
import com.devpaul.core_data.model.GoogleNewsXML
import com.devpaul.core_data.model.NewsPeruResponse
import com.devpaul.core_data.model.NewsResponse
import com.devpaul.core_data.model.RedditResponse
import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.GratitudeResponse
import com.devpaul.home.data.datasource.dto.response.SectionResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface HomeApi {

    @GET("api/rest/noticias.json")
    suspend fun newsFromPeru(): Response<MutableList<NewsPeruResponse>>

    @GET("api/v2/doc/doc")
    fun deltaProject(
        @Query("query") query: String,
        @Query("mode") mode: String,
        @Query("format") format: String
    ): Call<GDELProject>

    @GET("r/{country}/new.json")
    fun redditNews(
        @Path("country") country: String
    ): Call<RedditResponse>

    @GET("v2/top-headlines?apiKey=f206dd4aec1b46f38defa2ae5b0740e1")
    fun newsAPI(
        @Query("country") initLetters: String
    ): Call<NewsResponse>

    @GET("rss/search")
    fun googleNews(@Query("q") query: String, @Query("hl") language: String): Call<GoogleNewsXML>

    @GET("http://192.168.100.13:3000/home/dollarQuote")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("http://192.168.100.13:3000/home/uit")
    suspend fun uit(): Response<UITResponse>

    @GET("http://192.168.100.13:3000/home/uit")
    suspend fun sections(): Response<SectionResponse>

    @GET("http://192.168.100.13:3000/home/uit")
    suspend fun gratitude(): Response<GratitudeResponse>
}