package com.devpaul.profile.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class IncrementLikeResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
)