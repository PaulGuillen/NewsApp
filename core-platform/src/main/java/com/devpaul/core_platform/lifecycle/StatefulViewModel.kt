package com.devpaul.core_platform.lifecycle

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.base.UiStateHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

abstract class StatefulViewModel<UiState, UiIntent, UiEvent>(
    defaultUIState: () -> UiState,
    private val keySavedUIState: String = "UiState",
    val savedStateHandle: SavedStateHandle? = null,
) : StatelessViewModel<UiIntent, UiEvent>(), UiStateHolder<UiState> {

    // LiveData que mantiene el par (estado anterior, nuevo estado)
    private val _changeUiStateLiveData = MutableLiveData<Pair<UiState?, UiState>>(
        Pair(null, savedStateHandle?.get(keySavedUIState) ?: defaultUIState())
    )

    // LiveData con solo el estado actual
    private val _uiStateLiveData = MutableLiveData(_changeUiStateLiveData.value!!.second)

    // Acceso público a la observación del cambio de estado (antes -> después)
    override val changeUiStateLiveData: LiveData<Pair<UiState?, UiState>> get() = _changeUiStateLiveData

    // Acceso público al estado actual
    override val uiStateLiveData: LiveData<UiState> get() = _uiStateLiveData

    // Acceso rápido al valor actual del estado (sincrónico)
    override val uiState: UiState get() = uiStateLiveData.value!!

    /**
     * Actualiza el estado actual y notifica observadores si cambia.
     * También guarda el estado si es Parcelable (para recuperación en caso de reinicio).
     */
    override fun setUiState(uiState: UiState) {
        val oldValue = this.uiState
        if (oldValue == uiState) {
            Timber.d("🟡 setUiState: no change detected.")
            return
        }

        Timber.d("🟢 UiState changed:\nFrom: $oldValue\nTo:   $uiState")

        _changeUiStateLiveData.value = oldValue to uiState
        _uiStateLiveData.value = uiState

        // Guarda el estado en el SavedStateHandle si es serializable
        if (uiState is Parcelable) {
            savedStateHandle?.set(keySavedUIState, uiState)
        }
    }

    /**
     * Expone el estado como StateFlow para integración reactiva con Jetpack Compose.
     */
    val uiStateFlow: StateFlow<UiState> by lazy {
        uiStateLiveData.asFlow().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = uiState
        )
    }

    /**
     * Ejecuta una actualización del estado en el hilo principal y solo si cambia realmente.
     * Permite usar una lambda transformadora de `UiState -> UiState`.
     */
    protected suspend fun updateUiStateOnMain(block: (UiState) -> UiState) {
        withContext(Dispatchers.Main.immediate) {
            try {
                val oldState = uiState
                val newState = block(oldState)
                if (newState != oldState) {
                    Timber.d("🔄 updateUiStateOnMain:\nFrom: $oldState\nTo:   $newState")
                    setUiState(newState)
                } else {
                    Timber.d("🟡 updateUiStateOnMain: no state change.")
                }
            } catch (e: Exception) {
                Timber.e(e, "❌ Error in updateUiStateOnMain")
            }
        }
    }
}
