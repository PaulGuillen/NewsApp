package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UITEntity(
    val service: String? = null,
    val site: String? = null,
    val link: String? = null,
    val year: Int? = null,
    val value: Double? = null,
) : Parcelable