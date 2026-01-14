package com.devpaul.news.data.datasource.remote

import com.devpaul.news.data.datasource.dto.res.GoogleNewsXML
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GoogleApi {

    @GET("rss/search")
    suspend fun google(
        @Query("q") query: String,
        @Query("hl") mode: String,
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
    ): Response<GoogleNewsXML>
}