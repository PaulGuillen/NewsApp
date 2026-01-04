package com.devpaul.home.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DollarQuoteEntity(
    val service: String? = null,
    val site: String? = null,
    val link: String? = null,
    val prices: List<PriceItemEntity>? = null,
    val note: String? = null,
    val usdToEuro: String? = null,
    val date: String? = null,
) : Parcelable

@Parcelize
data class PriceItemEntity(
    val buy: Double? = null,
    val sell: Double? = null,
) : Parcelable