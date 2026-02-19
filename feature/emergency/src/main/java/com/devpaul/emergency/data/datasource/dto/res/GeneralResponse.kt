package com.devpaul.emergency.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GeneralResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("data")
    val data: List<GeneralResponseItem>,
)

data class GeneralResponseItem(
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: List<String>
)