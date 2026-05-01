package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class HolidayAlertResponse(
    @SerializedName("servicio") val service: String? = null,
    @SerializedName("fuente") val source: String? = null,
    @SerializedName("enlace") val link: String? = null,
    @SerializedName("fecha") val date: String? = null,
    @SerializedName("alerta") val alert: String? = null,
    @SerializedName("estado") val status: String? = null,
    @SerializedName("titulo") val title: String? = null,
    @SerializedName("ambito") val scope: String? = null,
    @SerializedName("dato") val data: String? = null,
)