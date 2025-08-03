package com.devpaul.profile.data.datasource.remote

import com.devpaul.profile.data.datasource.dto.req.CommentRequest
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.data.datasource.dto.res.PostResponse
import com.devpaul.profile.data.datasource.dto.res.CommentResponse
import com.devpaul.profile.data.datasource.dto.res.GetCommentResponse
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
import retrofit2.http.Query

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

    @POST("users/comment")
    suspend fun createComment(
        @Body request: CommentRequest
    ): Response<CommentResponse>

    @PATCH("users/posts/like/{type}/{id}/{userId}/{increment}")
    suspend fun incrementLike(
        @Path("type") type: String,
        @Path("id") commentId: String,
        @Path("userId") userId: String,
        @Path("increment") increment: Boolean
    ): Response<IncrementLikeResponse>

    @GET("users/posts")
    suspend fun getPost(): Response<PostResponse>

    @GET("users/comments")
    suspend fun getComments(
        @Query("limit") limit: Int,
        @Query("lastTimestamp") lastTimestamp: Long? = null
    ): Response<GetCommentResponse>

}