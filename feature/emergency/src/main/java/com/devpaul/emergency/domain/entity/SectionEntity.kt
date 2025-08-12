package com.devpaul.emergency.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SectionEntity(
    val status: Int,
    val data: List<SectionItemEntity>
) : Parcelable

@Parcelize
data class SectionItemEntity(
    val title: String,
    val image: String,
    val type: String
) : Parcelable