package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class SunatExchangeRateResponse(
    @SerializedName("origen") val source: String? = null,
    @SerializedName("compra") val buy: Double? = null,
    @SerializedName("venta") val sell: Double? = null,
    @SerializedName("moneda") val currency: String? = null,
    @SerializedName("fecha") val date: String? = null,
)