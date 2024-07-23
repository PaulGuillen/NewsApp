package com.devpaul.infoxperu.feature.user_start.login

sealed class LoginUiEvent {
    data class LoginSuccess(val message: String) : LoginUiEvent()
    data class LoginError(val error: String) : LoginUiEvent()
}
