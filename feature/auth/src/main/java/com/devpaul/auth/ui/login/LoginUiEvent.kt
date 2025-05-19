package com.devpaul.auth.ui.login

open class LoginUiEvent {
    data class LoginSuccess(val message: String) : LoginUiEvent()
    data class LoginError(val error: String) : LoginUiEvent()
    data class RecoveryPasswordSuccess(val message: String) : LoginUiEvent()
    data class RecoveryPasswordError(val error: String) : LoginUiEvent()
}