package com.devpaul.core_data.model

import com.google.gson.annotations.SerializedName

data class ArticleNewsResponse(
    @SerializedName("source") val source: SourceResponse,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val articleDescription: String?,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val imageUrl: String?,
    @SerializedName("publishedAt") val publishDate: String,
    @SerializedName("content") val content: String?
)

data class SourceResponse(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
)

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<ArticleNewsResponse>
)