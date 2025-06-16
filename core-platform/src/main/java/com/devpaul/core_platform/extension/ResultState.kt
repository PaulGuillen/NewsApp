package com.devpaul.core_platform.extension

sealed class ResultState<out T> {
    data object Loading : ResultState<Nothing>()
    data class Success<T>(val response: T) : ResultState<T>()
    data class Error(val message: String) : ResultState<Nothing>()
}