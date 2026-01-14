package com.devpaul.news.data.datasource.dto.res

import com.google.gson.annotations.SerializedName

data class GDELTResponse(
    @SerializedName("articles")
    val articleResponses: List<ArticleResponse>
)

data class ArticleResponse(
    @SerializedName("url")
    val url: String,
    @SerializedName("url_mobile")
    val urlMobile: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("seendate")
    val seenDate: String,
    @SerializedName("socialimage")
    val socialImage: String,
    @SerializedName("domain")
    val domain: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("sourcecountry")
    val sourceCountry: String
)