package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeltaProjectDataEntity(
    val articles: List<Article>,
) : Parcelable

@Parcelize
data class Article(
    val url: String,
    val urlMobile: String,
    val title: String,
    val seenDate: String,
    val socialImage: String,
    val domain: String,
    val language: String,
    val sourceCountry: String
) : Parcelable