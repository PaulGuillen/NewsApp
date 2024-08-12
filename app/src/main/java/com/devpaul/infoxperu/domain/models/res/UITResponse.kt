package devpaul.business.safetylima.data.models.response

import com.google.gson.annotations.SerializedName

class UITResponse(
    @SerializedName("servicio") val servicio: String? = null,
    @SerializedName("sitio") val sitio: String? = null,
    @SerializedName("enlace") val enlace: String? = null,
    @SerializedName("periodo") val periodo: Int? = null,
    @SerializedName("UIT") val UIT: Double? = null,

    ) {
    override fun toString(): String {
        return "UITResponse(servicio=$servicio, sitio=$sitio, enlace=$enlace, periodo=$periodo, UIT=$UIT)"
    }
}