package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GratitudeEntity(
    val status: Int,
    val message: String,
    val data: List<GratitudeDataEntity>
) : Parcelable

@Parcelize
data class GratitudeDataEntity(
    val id: String,
    val imageUrl: String,
    val title: String,
    val link: String
) : Parcelable