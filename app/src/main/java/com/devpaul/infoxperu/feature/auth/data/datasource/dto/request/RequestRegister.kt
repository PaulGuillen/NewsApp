package com.devpaul.infoxperu.feature.auth.data.datasource.dto.request

import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("userUID")
    val userUID: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("lastName")
    val lastName: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)