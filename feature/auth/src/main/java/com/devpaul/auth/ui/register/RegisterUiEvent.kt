package com.devpaul.auth.ui.register

sealed class RegisterUiEvent {
    data class RegisterError(val error: String) : RegisterUiEvent()
}