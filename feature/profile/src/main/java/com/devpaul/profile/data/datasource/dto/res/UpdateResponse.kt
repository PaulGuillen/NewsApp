package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)