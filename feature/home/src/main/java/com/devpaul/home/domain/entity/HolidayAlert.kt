package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HolidayAlertEntity(
    val service: String? = null,
    val source: String? = null,
    val link: String? = null,
    val date: String? = null,
    val alert: String? = null,
    val status: String? = null,
    val title: String? = null,
    val scope: String? = null,
    val data: String? = null,
) : Parcelable