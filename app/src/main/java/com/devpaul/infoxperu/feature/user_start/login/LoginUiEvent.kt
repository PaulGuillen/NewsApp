package com.devpaul.infoxperu.feature.user_start.login

sealed class LoginUiEvent {
    data class LoginSuccess(val message: String) : LoginUiEvent()
    data class LoginError(val error: String) : LoginUiEvent()
    data class RecoveryPasswordSuccess(val message: String) : LoginUiEvent()
    data class RecoveryPasswordError(val error: String) : LoginUiEvent()
}

sealed class LoginUiIntent {
    data class Login(val email: String, val password: String) : LoginUiIntent()
    data class ResetPassword(val email: String) : LoginUiIntent()
    data object CheckUserLoggedIn : LoginUiIntent()
}
