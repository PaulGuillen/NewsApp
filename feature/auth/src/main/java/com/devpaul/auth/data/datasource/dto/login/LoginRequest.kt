package com.devpaul.auth.data.datasource.dto.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("googleToken")
    val googleToken: String?,
)