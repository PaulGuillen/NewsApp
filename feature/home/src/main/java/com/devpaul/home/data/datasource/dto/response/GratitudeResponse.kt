package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class GratitudeResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<GratitudeItem>,
)

data class GratitudeItem(
    @SerializedName("id") val id: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val link: String
)