package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _uiEvent = MutableStateFlow<LoginUiEvent>(LoginUiEvent.LoadingFinished)
    val uiEvent: StateFlow<LoginUiEvent> = _uiEvent

    fun login(email: String, password: String) {
        _uiEvent.value = LoginUiEvent.LoadingStarted
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiEvent.value = LoginUiEvent.LoginSuccess("Login successful")
                    } else {
                        _uiEvent.value = LoginUiEvent.LoginError("Login failed")
                    }
                    _uiEvent.value = LoginUiEvent.LoadingFinished
                }
                .addOnFailureListener {
                    _uiEvent.value = LoginUiEvent.LoginError("Login failed")
                    _uiEvent.value = LoginUiEvent.LoadingFinished
                }
        }
    }
}
