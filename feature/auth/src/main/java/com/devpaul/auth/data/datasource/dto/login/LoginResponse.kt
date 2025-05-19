package com.devpaul.auth.data.datasource.dto.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("uid")
    val uid: String,
)