package com.devpaul.infoxperu.feature.auth.data.datasource.dto.request

import com.google.gson.annotations.SerializedName

data class RequestRegister(
    @SerializedName("name")
    val name: String,
    @SerializedName("lastname")
    val lastname: String?,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)