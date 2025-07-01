package com.devpaul.profile.data.datasource.dto.req

import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String,
    @SerializedName("birthdate")
    val birthdate: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)
