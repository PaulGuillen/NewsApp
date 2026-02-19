package com.devpaul.emergency.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PoliceEntity(
    val status: Int,
    val data: List<PoliceEntityItem>,
) : Parcelable

@Parcelize
data class PoliceEntityItem(
    val address: String,
    val district: String,
    val email: String,
    val phone: String,
    val unit: String,
) : Parcelable