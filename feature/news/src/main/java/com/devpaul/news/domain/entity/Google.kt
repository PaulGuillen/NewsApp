package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleEntity(
    val status: Int,
    val message: String,
    val data: GoogleNewsJSON
) : Parcelable

@Parcelize
data class GoogleNewsJSON(
    val title: String,
    val link: String,
    val language: String,
    val lastBuildDate: String?,
    val description: String?,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int,
    val newsItems: List<NewsItemJSON>
): Parcelable

@Parcelize
data class NewsItemJSON(
    val title: String,
    val link: String,
    val guid: String?,
    val pubDate: String?,
    val description: String?,
    val source: NewsSourceJSON
) : Parcelable

@Parcelize
data class NewsSourceJSON(
    val url: String,
    val name: String,
): Parcelable