package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class DollarQuoteResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: DollarQuoteData,
)

data class DollarQuoteData(
    @SerializedName("servicio") val service: String? = null,
    @SerializedName("sitio") val site: String? = null,
    @SerializedName("enlace") val link: String? = null,
    @SerializedName("Cotizacion") val prices: List<PriceItem>? = null,
    @SerializedName("importante") val note: String? = null,
    @SerializedName("DolaresxEuro") val usdToEuro: String? = null,
    @SerializedName("fecha") val date: String? = null,
)

data class PriceItem(
    @SerializedName("Compra") val buy: Double? = null,
    @SerializedName("Venta") val sell: Double? = null,
)