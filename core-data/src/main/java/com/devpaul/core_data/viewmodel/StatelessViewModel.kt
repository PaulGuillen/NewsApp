package com.devpaul.core_data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devpaul.core_data.util.Constant
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class StatelessViewModel<T, I>(
    private val dataStoreUseCase: DataStoreUseCase? = null,
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiEvent = MutableStateFlow<T?>(null)
    open val uiEvent: StateFlow<T?> = _uiEvent

    private val uiIntentChannel = Channel<I>(Channel.BUFFERED)

    init {
        viewModelScope.launch {
            uiIntentChannel.receiveAsFlow().collect { intent ->
                handleIntent(intent)
            }
        }
    }

    fun executeInScope(
        block: suspend () -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                block()
            } catch (e: Throwable) {
                handleGlobalError(e, onError)
            } finally {
                onComplete?.invoke()
            }
        }
    }

    private fun handleGlobalError(error: Throwable, onError: ((Throwable) -> Unit)? = null) {
        onError?.invoke(error)
    }

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    protected fun setUiEvent(event: T) {
        _uiEvent.value = event
    }

    fun resetUiEvent() {
        _uiEvent.value = null
    }

    fun executeUiIntent(intent: I) {
        uiIntentChannel.trySendBlocking(intent)
    }

    protected open fun handleIntent(intent: I) {
        // Subclasses should override to handle intents
    }

    fun logOut(navHostController: NavHostController) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
            dataStoreUseCase?.setValue(Constant.LOG_IN_KEY, false)
            navHostController.navigate(Constant.LOG_IN_KEY) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}