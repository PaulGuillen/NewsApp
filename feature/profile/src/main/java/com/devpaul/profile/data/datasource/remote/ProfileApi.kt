package com.devpaul.profile.data.datasource.remote

import com.devpaul.profile.data.datasource.dto.req.CreatePostRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.dto.res.AllPostsResponse
import com.devpaul.profile.data.datasource.dto.res.CreatePostResponse
import com.devpaul.profile.data.datasource.dto.res.IncrementLikeResponse
import com.devpaul.profile.data.datasource.dto.res.ProfileResponse
import com.devpaul.profile.data.datasource.dto.res.UpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface ProfileApi {

    @GET("users/profile/{id}")
    suspend fun getProfileById(
        @Path("id") uid: String
    ): Response<ProfileResponse>

    @PUT("users/profile/update/{id}")
    suspend fun updateUserData(
        @Path("id") uid: String,
        @Body profileUserEntity: UpdateRequest
    ): Response<UpdateResponse>

    @POST("users/posts")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): Response<CreatePostResponse>

    @PATCH("users/posts/like/{id}")
    suspend fun incrementLike(
        @Path("id") commentId: String
    ): Response<IncrementLikeResponse>

    @GET("users/posts")
    suspend fun getAllPosts(): Response<AllPostsResponse>
}