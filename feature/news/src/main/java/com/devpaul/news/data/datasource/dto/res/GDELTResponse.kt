package com.devpaul.news.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GDELTResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: GDELTData
)

data class GDELTData(
    @SerializedName("items") val items: List<GDELTItem>,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("perPage") val perPage: Int
)

data class GDELTItem(
    @SerializedName("url") val url: String,
    @SerializedName("urlMobile") val urlMobile: String,
    @SerializedName("title") val title: String,
    @SerializedName("seenDate") val seenDate: String,
    @SerializedName("socialImage") val socialImage: String,
    @SerializedName("domain") val domain: String,
    @SerializedName("language") val language: String,
    @SerializedName("sourceCountry") val sourceCountry: String
)