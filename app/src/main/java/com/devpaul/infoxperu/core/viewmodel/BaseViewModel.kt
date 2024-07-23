package com.devpaul.infoxperu.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T> : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiEvent = MutableStateFlow<T?>(null)
    open val uiEvent: StateFlow<T?> = _uiEvent

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    protected fun setUiEvent(event: T) {
        _uiEvent.value = event
    }

    fun resetUiEvent() {
        _uiEvent.value = null
    }
}
