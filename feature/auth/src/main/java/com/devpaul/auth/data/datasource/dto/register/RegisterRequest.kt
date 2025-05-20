package com.devpaul.auth.data.datasource.dto.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("userUID")
    val userUID: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)