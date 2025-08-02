package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: List<PostItemResponse>
)

data class PostItemResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("postId")
    val postId: String? = null,
    @SerializedName("toPublic")
    val toPublic: Boolean? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("comment")
    val createdAt: CreatedAtResponse? = null,
    @SerializedName("likes")
    val likes: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("likedBy")
    val likedBy: Map<String, Boolean>? = null
)

data class CreatedAtResponse(
    @SerializedName("_seconds")
    val seconds: Long,
    @SerializedName("_nanoseconds")
    val nanoseconds: Int
)