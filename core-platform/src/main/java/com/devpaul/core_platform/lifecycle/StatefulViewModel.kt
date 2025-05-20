package com.devpaul.core_platform.lifecycle

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.devpaul.core_platform.lifecycle.base.UiStateHolder
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

abstract class StatefulViewModel<UiState, UiIntent, UiEvent>(
    defaultUIState: () -> UiState,
    private val keySavedUIState: String = "UiState",
    private val savedStateHandle: SavedStateHandle? = null,
) : StatelessViewModel<UiIntent, UiEvent>(), UiStateHolder<UiState> {

    private val _changeUiStateLiveData = MutableLiveData<Pair<UiState?, UiState>>(
        Pair(null, savedStateHandle?.get<UiState>(keySavedUIState) ?: defaultUIState())
    )
    private val _uiStateLiveData = MutableLiveData(_changeUiStateLiveData.value!!.second)

    override val changeUiStateLiveData: LiveData<Pair<UiState?, UiState>> get() = _changeUiStateLiveData
    override val uiStateLiveData: LiveData<UiState> get() = _uiStateLiveData
    override val uiState: UiState get() = uiStateLiveData.value!!

    override fun setUiState(uiState: UiState) {
        val oldValue = this.uiState
        if (oldValue === uiState) return
        _changeUiStateLiveData.value = oldValue to uiState
        _uiStateLiveData.value = uiState
        if (uiState is Parcelable) {
            savedStateHandle?.set(keySavedUIState, uiState)
        }
    }

    val uiStateFlow: StateFlow<UiState> by lazy {
        uiStateLiveData.asFlow().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), uiState)
    }
}