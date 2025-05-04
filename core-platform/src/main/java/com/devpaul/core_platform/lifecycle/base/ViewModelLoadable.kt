package com.devpaul.core_platform.lifecycle.base

interface ViewModelLoadable {

    var isLoading: Boolean

    suspend fun onLoading(listener: (isLoading: Boolean) -> Unit)
}