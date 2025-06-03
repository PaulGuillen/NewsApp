package com.devpaul.news.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryEntity(
    val status: Int,
    val message: String,
    val data: List<CountryItemEntity>
) : Parcelable

@Parcelize
data class CountryItemEntity(
    val id: String,
    val summary: String,
    val title: String,
    val category: String,
    val imageUrl: String,
    val initLetters: String
) : Parcelable