package com.devpaul.emergency.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralEntity(
    val status: Int,
    val data: List<GeneralEntityItem>,
) : Parcelable

@Parcelize
data class GeneralEntityItem(
    val key: String,
    val value: List<String>
) : Parcelable