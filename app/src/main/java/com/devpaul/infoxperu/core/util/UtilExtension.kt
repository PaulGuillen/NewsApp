package com.devpaul.infoxperu.core.util

import org.json.JSONException
import org.json.JSONObject

fun getErrorMessageFromThrowable(error: Throwable): String {
    return try {
        val errorJson = JSONObject(error.message ?: "")
        errorJson.optString("message", "Error desconocido")
    } catch (jsonException: JSONException) {
        error.message ?: "Error desconocido"
    }
}
