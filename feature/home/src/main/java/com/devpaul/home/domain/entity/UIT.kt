package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UITEntity(
    val status: Int,
    val message: String,
    val data: UITDataEntity,
) : Parcelable

@Parcelize
data class UITDataEntity(
    val service: String? = null,
    val site: String? = null,
    val link: String? = null,
    val year: Int? = null,
    val value: Double? = null,
    val imageUrl: String? = null,
    val iconImage: String? = null,
) : Parcelable