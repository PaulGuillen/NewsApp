package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeltaProjectEntity(
    val status: Int,
    val message: String,
    val data: DeltaProjectDataEntity
) : Parcelable

@Parcelize
data class DeltaProjectDataEntity(
    val items: List<DeltaProjectItemEntity>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int
) : Parcelable

@Parcelize
data class DeltaProjectItemEntity(
    val url: String,
    val urlMobile: String,
    val title: String,
    val seenDate: String,
    val socialImage: String,
    val domain: String,
    val language: String,
    val sourceCountry: String
) : Parcelable