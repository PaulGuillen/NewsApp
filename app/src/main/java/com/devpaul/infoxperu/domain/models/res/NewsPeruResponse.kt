package devpaul.business.safetylima.data.models.response

import com.google.gson.annotations.SerializedName

class NewsPeruResponse(
    @SerializedName("titulo") val titulo: String?,
    @SerializedName("resumen") val resumen: String?,
    @SerializedName("palsclave") val palsclave: String?,
    @SerializedName("imagen") val imagen: String?,
    @SerializedName("imagenpeque") val imagenpeque: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("fecha") val fecha: String?,

    ) {
    override fun toString(): String {
        return "NewsPeruResponse(titulo=$titulo, resumen=$resumen, palsclave=$palsclave, imagen=$imagen, imagenpeque=$imagenpeque, url=$url, fecha=$fecha)"
    }
}