package com.devpaul.infoxperu.feature.auth.data.datasource.dto.request

import com.google.gson.annotations.SerializedName

data class RequestLogin(
    @SerializedName("email")
    val email: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("googleToken")
    val googleToken: String?,
)