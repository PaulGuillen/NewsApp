package com.devpaul.infoxperu.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>(
    private val dataStoreUseCase: DataStoreUseCase? = null
) : ViewModel() {

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

    fun logOut(navHostController: NavHostController) {
        viewModelScope.launch {
            FirebaseAuth.getInstance().signOut()
            dataStoreUseCase?.setValue("logIn", false)
            navHostController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

}
