package com.devpaul.infoxperu.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class StatelessViewModel<T, I>(
    private val dataStoreUseCase: DataStoreUseCase? = null
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

    protected fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    protected fun setUiEvent(event: T) {
        _uiEvent.value = event
    }

    fun resetUiEvent() {
        _uiEvent.value = null
    }

    fun logOut(navHostController: NavHostController) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
            dataStoreUseCase?.setValue("logIn", false)
            navHostController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    fun executeUiIntent(intent: I) {
        uiIntentChannel.trySendBlocking(intent)
    }

    /**
     * Override this method to handle specific intents in subclasses.
     */
    protected open suspend fun handleIntent(intent: I) {
        // Subclasses should override to handle intents
    }
}
