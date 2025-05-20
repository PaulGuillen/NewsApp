package com.devpaul.auth.data.datasource.dto.register

import com.google.gson.annotations.SerializedName

class RegisterResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("uid")
    val uid: String,
)