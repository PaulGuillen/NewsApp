package com.devpaul.emergency.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class SectionResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: List<SectionItemResponse>
)

data class SectionItemResponse(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("type") val type: String
)