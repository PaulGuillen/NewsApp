package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GoogleEntity(
    val status: Int,
    val message: String,
    val data: GoogleDataEntity
) : Parcelable

@Parcelize
data class GoogleDataEntity(
    val items: List<GoogleNewsItemEntity>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val perPage: Int
) : Parcelable

@Parcelize
data class GoogleNewsItemEntity(
    val title: String,
    val link: String,
    val description: String,
    val pubDate: String,
    val source: SourceEntity,
    val guid: GuidEntity
) : Parcelable

@Parcelize
data class SourceEntity(
    val url: String,
    val name: String
) : Parcelable

@Parcelize
data class GuidEntity(
    val value: String,
    val metadata: GuidMetaEntity
) : Parcelable

@Parcelize
data class GuidMetaEntity(
    val isPermLink: String
) : Parcelable