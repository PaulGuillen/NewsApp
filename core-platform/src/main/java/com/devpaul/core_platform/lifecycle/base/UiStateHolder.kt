package com.devpaul.core_platform.lifecycle.base

import androidx.lifecycle.LiveData

interface UiStateHolder<UiState> {

    val uiState: UiState
    val uiStateLiveData: LiveData<UiState>
    val changeUiStateLiveData: LiveData<Pair<UiState?, UiState>>
    fun setUiState(uiState: UiState)
}