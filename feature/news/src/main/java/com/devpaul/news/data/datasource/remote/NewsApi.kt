package com.devpaul.news.data.datasource.remote

import com.devpaul.news.data.datasource.dto.res.CountryResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse
import com.devpaul.news.data.datasource.dto.res.GoogleResponse
import com.devpaul.news.data.datasource.dto.res.RedditResponse
import retrofit2.Response
import retrofit2.http.GET

internal interface NewsApi {

    @GET("http://192.168.100.13:3000/news/countries")
    suspend fun country(): Response<CountryResponse>

    @GET("http://192.168.100.13:3000/news/countries")
    suspend fun google(): Response<GoogleResponse>

    @GET("http://192.168.100.13:3000/news/countries")
    suspend fun deltaProject(): Response<GDELTResponse>

    @GET("http://192.168.100.13:3000/news/countries")
    suspend fun reddit(): Response<RedditResponse>
}