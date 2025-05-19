package com.devpaul.core_platform.lifecycle

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class StatefulViewModel<UiState, UiIntent, UiEvent>(
    private val defaultUIState: () -> UiState
) : ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    // --- STATE ---
    private val _uiState = MutableStateFlow(defaultUIState())
    val uiState: StateFlow<UiState> = _uiState

    fun setUiState(state: UiState) {
        _uiState.value = state
    }

    // --- EVENT (one-shot) ---
    private val _uiEvent = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }

    // --- INTENT ---
    abstract suspend fun onUiIntent(intent: UiIntent)

    fun executeUiIntent(intent: UiIntent) {
        viewModelScope.launch {
            onUiIntent(intent)
        }
    }
}
