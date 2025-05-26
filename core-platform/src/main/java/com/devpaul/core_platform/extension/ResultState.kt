package com.devpaul.core_platform.extension

sealed class ResultState<out T> {
    data class Success<out T>(val response: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
}
