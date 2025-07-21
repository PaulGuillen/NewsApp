package com.devpaul.profile.data.datasource.dto.req

import com.google.gson.annotations.SerializedName

data class CreatePostRequest(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("comment")
    val comment: String
)