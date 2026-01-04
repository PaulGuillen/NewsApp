package com.devpaul.auth.ui.login

open class LoginUiEvent {
    data object UserLogged : LoginUiEvent()
    data class RecoveryPasswordSuccess(val message: String) : LoginUiEvent()
    data class RecoveryPasswordError(val error: String) : LoginUiEvent()
}