package com.devpaul.infoxperu.feature.auth.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

class ResponseRecoveryPassword (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)