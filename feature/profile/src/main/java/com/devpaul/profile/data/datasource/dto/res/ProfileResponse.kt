package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProfileUserResponse
)

class ProfileUserResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("uid")
    val uid: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("birthdate")
    val birthdate: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("image")
    val image: String? = null
)