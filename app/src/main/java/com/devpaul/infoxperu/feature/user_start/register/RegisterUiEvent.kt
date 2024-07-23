package com.devpaul.infoxperu.feature.user_start.register

import com.devpaul.infoxperu.feature.user_start.login.LoginUiEvent

sealed class RegisterUiEvent {
    data class RegisterSuccess(val message: String) : RegisterUiEvent()
    data class RegisterError(val error: String) : RegisterUiEvent()
}