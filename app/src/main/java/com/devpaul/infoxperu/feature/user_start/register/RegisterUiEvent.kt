package com.devpaul.infoxperu.feature.user_start.register

sealed class RegisterUiEvent {
    data class RegisterSuccess(val message: String) : RegisterUiEvent()
    data class RegisterError(val error: String) : RegisterUiEvent()
}

sealed class RegisterUiIntent {
    data class Register(
        val name: String,
        val lastname: String,
        val email: String,
        val password: String,
    ) : RegisterUiIntent()
}