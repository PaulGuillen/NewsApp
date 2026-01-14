package com.devpaul.news.data.datasource.remote

import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.news.data.datasource.dto.res.CountryResponse
import com.devpaul.news.data.datasource.dto.res.GDELTResponse

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface NewsApi {

    @GET("news/countries")
    suspend fun country(): Response<CountryResponse>

    @GET("api/v2/doc/doc")
    suspend fun deltaProject(
        @Query("query") query: String,
        @Query("mode") mode: String,
        @Query("format") format: String
    ): Response<GDELTResponse>

    @GET("r/{country}/new.json")
    suspend fun redditNews(
        @Path("country") country: String
    ): Response<RedditResponse>

}