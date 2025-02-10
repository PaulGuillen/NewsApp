package com.devpaul.infoxperu.feature.auth.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

class ResponseRegister (
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("uid")
    val uid: String?,
)