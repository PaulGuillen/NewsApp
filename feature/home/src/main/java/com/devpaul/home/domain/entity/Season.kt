package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeasonEntity(
    val years: Map<String, SeasonYearEntity> = emptyMap(),
) : Parcelable

@Parcelize
data class SeasonYearEntity(
    val autumn: SeasonDetailEntity? = null,
    val winter: SeasonDetailEntity? = null,
    val spring: SeasonDetailEntity? = null,
    val summer: SeasonDetailEntity? = null,
) : Parcelable

@Parcelize
data class SeasonDetailEntity(
    val startHour: String? = null,
    val startDay: String? = null,
    val startMonth: String? = null,
    val startDate: String? = null,
    val startText: String? = null,
    val endDate: String? = null,
) : Parcelable