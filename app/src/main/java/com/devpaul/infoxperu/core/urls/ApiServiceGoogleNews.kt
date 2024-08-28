package com.devpaul.infoxperu.core.urls

import com.devpaul.infoxperu.domain.models.res.GoogleNewsXML
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceGoogleNews {
    @GET("rss/search")
    fun googleNews(@Query("q") query: String, @Query("hl") language: String): Call<GoogleNewsXML>
}