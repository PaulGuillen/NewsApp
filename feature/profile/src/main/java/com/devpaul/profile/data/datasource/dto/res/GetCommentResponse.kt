package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GetCommentResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("comments")
    val comments: List<GetCommentDataResponse>,
    @SerializedName("nextPageCursor")
    val nextPageCursor: Long? = null,
)

data class GetCommentDataResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("commentId")
    val commentId: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("createdAt")
    val createdAt: CreatedAtResponse,
    @SerializedName("likes")
    val likes: Int,
)