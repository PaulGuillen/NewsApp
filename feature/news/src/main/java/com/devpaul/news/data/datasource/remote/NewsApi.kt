package com.devpaul.news.data.datasource.remote

import com.devpaul.news.data.datasource.dto.res.CountryResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.data.datasource.dto.res.GoogleResponse
import com.devpaul.news.data.datasource.dto.res.RedditResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NewsApi {

    @GET("news/countries")
    suspend fun country(): Response<CountryResponse>

    @GET("news/google")
    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    suspend fun google(
        @Query("q") query: String,
        @Query("hl") mode: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GoogleResponse>

    @GET("news/gdelt")
    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    suspend fun deltaProject(
        @Query("q") query: String,
        @Query("mode") mode: String,
        @Query("format") format: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GDELTResponse>

    @GET("news/reddit")
    //@GET("https://run.mocky.io/v3/df06e3d5-b9db-4596-9b4e-d1fa84d37fc7")
    suspend fun reddit(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<RedditResponse>
}