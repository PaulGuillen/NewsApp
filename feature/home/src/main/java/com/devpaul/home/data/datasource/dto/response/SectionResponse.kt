package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class SectionResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<SectionItem>,
)

data class SectionItem(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("type") val type: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("description") val description: String,
    @SerializedName("destination") val destination: String,
)