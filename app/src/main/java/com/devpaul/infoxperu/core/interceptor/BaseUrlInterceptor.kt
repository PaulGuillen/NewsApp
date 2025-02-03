package com.devpaul.infoxperu.core.interceptor

import com.devpaul.infoxperu.BuildConfig
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.util.regex.Pattern

class BaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url

        // Aquí usamos la URL original si no hay coincidencia con los casos previos
        val newBaseUrl = when {
            originalUrl.encodedPath.containsRegex("r/.+/new.json") -> BuildConfig.BASE_URL_REDDIT
            originalUrl.encodedPath.contains("top-headline") -> BuildConfig.BASE_URL_NEWS
            originalUrl.encodedPath.contains("rss/search") -> BuildConfig.BASE_URL_GOOGLE_NEWS
            originalUrl.encodedPath.contains("v2/doc/doc") -> BuildConfig.BASE_URL_GDELT_PROJECT
            else -> originalUrl.toString() // No modificamos la URL base, usamos la original
        }.toHttpUrlOrNull()

        // Si no se modificó la URL, simplemente usamos la original
        val newUrl = newBaseUrl?.newBuilder()
            ?.encodedPath(originalUrl.encodedPath)
            ?.query(originalUrl.query)
            ?.build() ?: originalUrl  // Si no se cambió la URL, mantén la original

        // Construir la nueva solicitud con la URL modificada
        val newRequestBuilder = request.newBuilder().url(newUrl)

        // Agregar encabezados adicionales si la URL base es la predeterminada (por ejemplo, Perú)
        if (newBaseUrl?.toString()?.contains(BuildConfig.BASE_URL_PERU) == true) {
            newRequestBuilder.addHeader("User-Agent", "devpaul")
        }

        // Agregar encabezado "Accept-Encoding" si la URL contiene "rss/search" o coincide con el patrón de Reddit
        if (originalUrl.encodedPath.contains("rss/search") || originalUrl.encodedPath.containsRegex(
                "r/.+/new.json"
            )
        ) {
            newRequestBuilder.addHeader("Accept-Encoding", "identity")
        }

        // Continuar con la solicitud con la nueva configuración
        return chain.proceed(newRequestBuilder.build())
    }
}

private fun String.containsRegex(regex: String): Boolean {
    return Pattern.compile(regex).matcher(this).find()
}

