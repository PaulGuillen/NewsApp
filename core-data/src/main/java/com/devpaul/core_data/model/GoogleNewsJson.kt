package com.devpaul.core_data.model

data class GoogleNewsJSON(
    val title: String,
    val link: String,
    val language: String,
    val lastBuildDate: String?,
    val description: String?,
    val newsItems: List<NewsItemJSON>
)

data class NewsItemJSON(
    val title: String,
    val link: String,
    val guid: String?,
    val pubDate: String?,
    val description: String?,
    val source: NewsSourceJSON
)

data class NewsSourceJSON(
    val url: String,
    val name: String
)