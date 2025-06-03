package com.devpaul.news.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<CountryItem>
)

data class CountryItem(
    @SerializedName("id") val id: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("title") val title: String,
    @SerializedName("category") val category: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("initLetters") val initLetters: String
)