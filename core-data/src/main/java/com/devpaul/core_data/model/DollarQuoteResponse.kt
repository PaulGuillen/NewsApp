package com.devpaul.core_data.model

import com.google.gson.annotations.SerializedName

data class DollarQuoteResponse(
    @SerializedName("servicio") val servicio: String? = null,
    @SerializedName("sitio") val sitio: String? = null,
    @SerializedName("enlace") val enlace: String? = null,
    @SerializedName("Cotizacion") val cotizacion: List<CotizacionItem>? = null,
    @SerializedName("importante") val importante: String? = null,
    @SerializedName("DolaresxEuro") val dolaresxEuro: String? = null,
    @SerializedName("fecha") val fecha: String? = null
)

data class CotizacionItem(
    @SerializedName("Compra") val compra: Double? = null,
    @SerializedName("Venta") val venta: Double? = null
)
