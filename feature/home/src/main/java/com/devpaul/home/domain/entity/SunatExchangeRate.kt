package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SunatExchangeRateEntity(
    val source: String? = null,
    val buy: Double? = null,
    val sell: Double? = null,
    val currency: String? = null,
    val date: String? = null,
) : Parcelable