package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SectionEntity(
    val status: Int,
    val message: String,
    val data: List<SectionDataEntity>
) : Parcelable

@Parcelize
data class SectionDataEntity(
    val id: String,
    val title: String,
    val type: String
) : Parcelable