package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class AllPostsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("posts")
    val posts: List<PostItemResponse>,
    @SerializedName("data")
    val data: List<PostItemResponse>
)

data class PostItemResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("commentId")
    val commentId: String? = null,
    @SerializedName("userId")
    val userId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("comment")
    val comment: String? = null,
    @SerializedName("createdAt")
    val createdAt: CreatedAtResponse? = null,
    @SerializedName("likes")
    val likes: Int? = null,
    @SerializedName("postId")
    val postId: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("toPublic")
    val toPublic: Boolean? = null
)

data class CreatedAtResponse(
    @SerializedName("_seconds")
    val seconds: Long,
    @SerializedName("_nanoseconds")
    val nanoseconds: Int
)