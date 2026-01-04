package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class UITResponse(
    @SerializedName("servicio") val service: String? = null,
    @SerializedName("sitio") val site: String? = null,
    @SerializedName("enlace") val link: String? = null,
    @SerializedName("periodo") val year: Int? = null,
    @SerializedName("UIT") val value: Double? = null,
)