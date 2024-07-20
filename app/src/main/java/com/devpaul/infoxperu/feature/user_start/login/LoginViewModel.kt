package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel() {

    private val _uiEvent = MutableStateFlow<LoginUiEvent>(LoginUiEvent.Idle)
    val uiEvent: StateFlow<LoginUiEvent> = _uiEvent

    fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    setLoading(false)
                    if (task.isSuccessful) {
                        _uiEvent.value = LoginUiEvent.LoginSuccess("Login successful")
                    } else {
                        _uiEvent.value = LoginUiEvent.LoginError("Login failed")
                    }
                }
                .addOnFailureListener {
                    setLoading(false)
                    _uiEvent.value = LoginUiEvent.LoginError("Login failed")
                }
        }
    }

    fun resetUiEvent() {
        _uiEvent.value = LoginUiEvent.Idle
    }
}
