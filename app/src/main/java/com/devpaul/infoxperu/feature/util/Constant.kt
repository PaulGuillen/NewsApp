package com.devpaul.infoxperu.feature.util

object Constant {
    // DataStore Keys
    const val LOG_IN_KEY = "logIn"

    // Messages
    const val LOGIN_SUCCESS = "Inicio de sesión exitoso"
    const val LOGIN_FAILURE = "Inicio de sesión fallido"
    const val LOGIN_ERROR = "Error en el inicio de sesión: "
    const val PASSWORD_RECOVERY_SUCCESS = "Correo de recuperación enviado"
    const val PASSWORD_RECOVERY_FAILURE = "Error al enviar el correo de recuperación"
    const val PASSWORD_RECOVERY_ERROR = "Error al enviar el correo de recuperación: "
    const val USER_ALREADY_LOGGED_IN = "Usuario ya autenticado"

    // Other constants
    const val LOGIN_DELAY = 3000L // Delay in milliseconds
}