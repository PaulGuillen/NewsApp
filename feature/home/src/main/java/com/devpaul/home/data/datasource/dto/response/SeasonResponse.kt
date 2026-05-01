package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

typealias SeasonResponse = Map<String, SeasonYearResponse>

data class SeasonYearResponse(
    @SerializedName("Oto\u00f1o") val autumn: SeasonDetailResponse? = null,
    @SerializedName("Invierno") val winter: SeasonDetailResponse? = null,
    @SerializedName("Primavera") val spring: SeasonDetailResponse? = null,
    @SerializedName("Verano") val summer: SeasonDetailResponse? = null,
)

data class SeasonDetailResponse(
    @SerializedName("horaInicio") val startHour: String? = null,
    @SerializedName("diaInicio") val startDay: String? = null,
    @SerializedName("mesInicio") val startMonth: String? = null,
    @SerializedName("fechaInicio") val startDate: String? = null,
    @SerializedName("textoInicio") val startText: String? = null,
    @SerializedName("fechaFinal") val endDate: String? = null,
)