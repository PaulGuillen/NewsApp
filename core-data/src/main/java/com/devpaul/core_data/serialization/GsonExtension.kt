package com.devpaul.core_data.serialization

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> Gson.fromJsonGeneric(json: String?): T {
    return this.fromJson(json, object : TypeToken<T>() {}.type)
}