package com.devpaul.profile.data.datasource.remote

import com.devpaul.profile.data.datasource.dto.res.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ProfileApi {

    @GET("users/profile/{id}")
    suspend fun getProfileById(
        @Path("id") uid: String
    ): Response<ProfileResponse>
}