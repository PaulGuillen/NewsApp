package com.devpaul.navigation.core

import android.os.Bundle
import android.util.Base64
import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

inline fun <reified T: Any> getSafeArgs(base64SafeArgs: String): T {
    if (base64SafeArgs.isEmpty()
        || base64SafeArgs == Constants.EMPTY_ARG
        || base64SafeArgs == Constants.REPLACE_ARG_NAME
    ) {
        return Json.decodeFromString(Constants.EMPTY_ARG)
    }
    val byteArraySafeArgs = Base64.decode(base64SafeArgs, Base64.DEFAULT)
    val stringSafeArgs = String(byteArraySafeArgs, Charsets.UTF_8)
    return Json.decodeFromString(stringSafeArgs)
}

inline fun <reified T: Any> Bundle.getSafeArgs(): T {
    val base64SafeArgs = getString(Constants.ARG_NAME, "")
    return getSafeArgs(base64SafeArgs)
}

inline fun <reified T: Any> SavedStateHandle.getSafeArgs(): T {
    val base64SafeArgs = this.get<String?>(Constants.ARG_NAME).orEmpty()
    return getSafeArgs(base64SafeArgs)
}


inline fun <reified T> ModularDestination.createSafeArgs(
    value: T
): String {
    val jsonSafeArgs = Json.encodeToString(asKSerializer(), value)
    val base64SafeArgs = Base64.encodeToString(
        jsonSafeArgs.toByteArray(Charsets.UTF_8),
        Base64.DEFAULT
    )
    return asString().replace(
        Constants.REPLACE_ARG_NAME,
        base64SafeArgs
    )
}

@Suppress("UNCHECKED_CAST")
fun <T> ModularDestination.asKSerializer(): KSerializer<T> = kSerializer as KSerializer<T>

fun ModularDestination.asString(): String = route