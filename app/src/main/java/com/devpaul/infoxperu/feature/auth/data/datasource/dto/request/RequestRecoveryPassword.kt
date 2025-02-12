package com.devpaul.infoxperu.feature.auth.data.datasource.dto.request

import com.google.gson.annotations.SerializedName

data class RequestRecoveryPassword(
    @SerializedName("email")
    val email: String,
)