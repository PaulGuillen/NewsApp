package com.devpaul.infoxperu.feature.user_start.login

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : BaseViewModel<LoginUiEvent>() {

    fun login(email: String, password: String) {
        setLoading(true)
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    setLoading(false)
                    if (task.isSuccessful) {
                        setUiEvent(LoginUiEvent.LoginSuccess("Login successful"))
                    } else {
                        setUiEvent(LoginUiEvent.LoginError("Login failed"))
                    }
                }
                .addOnFailureListener {
                    setLoading(false)
                    setUiEvent(LoginUiEvent.LoginError("Login failed"))
                }
        }
    }
}
