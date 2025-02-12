package com.devpaul.infoxperu.core.urls

import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.NewsPeruResponse
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRecoveryPassword
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.request.RequestRegister
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseLogin
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseRecoveryPassword
import com.devpaul.infoxperu.feature.auth.data.datasource.dto.response.ResponseRegister
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/rest/noticias.json")
    suspend fun newsFromPeru(): Response<MutableList<NewsPeruResponse>>

    @GET("api/rest/cotizaciondolar.json")
    suspend fun dollarQuote(): Response<DollarQuoteResponse>

    @GET("api/rest/uitperu.json")
    suspend fun dataUIT(): Response<UITResponse>

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

    @POST("http://192.168.100.13:3000/users/login")
    suspend fun login(
        @Body login: RequestLogin
    ): Response<ResponseLogin>

    @POST("http://192.168.100.13:3000/users/register")
    suspend fun register(
        @Body register: RequestRegister
    ): Response<ResponseRegister>

    @POST("http://192.168.100.13:3000/users/recoveryPassword")
    suspend fun recoveryPassword(
        @Body recoveryPassword: RequestRecoveryPassword
    ): Response<ResponseRecoveryPassword>
}