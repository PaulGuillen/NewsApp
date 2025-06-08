package com.devpaul.news.data.datasource.remote

import com.devpaul.news.data.datasource.dto.res.CountryResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.data.datasource.dto.res.GoogleResponse
import com.devpaul.news.data.datasource.dto.res.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {

    @GET("http://192.168.100.137:3000/news/countries")
    suspend fun country(): Response<CountryResponse>

    @GET("http://192.168.100.137:3000/news/google")
    suspend fun google(
        @Query("q") query: String,
        @Query("hl") mode: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GoogleResponse>

    @GET("http://192.168.100.137:3000/news/gdelt")
    suspend fun deltaProject(
        @Query("q") query: String,
        @Query("mode") mode: String,
        @Query("format") format: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GDELTResponse>

    @GET("http://192.168.100.137:3000/news/reddit")
    suspend fun reddit(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<RedditResponse>
}