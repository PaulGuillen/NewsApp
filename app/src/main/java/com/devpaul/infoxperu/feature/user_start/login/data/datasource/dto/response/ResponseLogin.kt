package com.devpaul.infoxperu.feature.user_start.login.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("uid")
    val uid: String?,
)