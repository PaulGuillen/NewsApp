package com.devpaul.home.data.datasource.dto.response

import com.google.gson.annotations.SerializedName

data class UITResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UITData,
)

data class UITData(
    @SerializedName("servicio") val service: String? = null,
    @SerializedName("sitio") val site: String? = null,
    @SerializedName("enlace") val link: String? = null,
    @SerializedName("periodo") val year: Int? = null,
    @SerializedName("UIT") val value: Double? = null,
    @SerializedName("imageUrl") val imageUrl: String? = null,
    @SerializedName("iconImage") val iconImage: String? = null,
)