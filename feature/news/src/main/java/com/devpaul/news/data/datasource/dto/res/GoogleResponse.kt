package com.devpaul.news.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GoogleResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: GoogleData
)

data class GoogleData(
    @SerializedName("items") val items: List<GoogleNewsItem>,
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("perPage") val perPage: Int
)

data class GoogleNewsItem(
    @SerializedName("title") val title: String,
    @SerializedName("link") val link: String,
    @SerializedName("description") val description: String,
    @SerializedName("pubDate") val pubDate: String,
    @SerializedName("source") val source: Source,
    @SerializedName("guid") val guid: Guid
)

data class Source(
    @SerializedName("url") val url: String,
    @SerializedName("name") val name: String
)

data class Guid(
    @SerializedName("_") val value: String,
    @SerializedName("\$") val metadata: GuidMeta
)

data class GuidMeta(
    @SerializedName("isPermaLink") val isPermLink: String
)