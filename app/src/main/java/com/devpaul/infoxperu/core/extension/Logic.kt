package com.devpaul.infoxperu.core.extension

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validateRegistration(
    name: String,
    lastName: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    return when {
        name.isEmpty() -> "El nombre no puede estar vacío"
        lastName.isEmpty() -> "El apellido no puede estar vacío"
        email.isEmpty() -> "El correo no puede estar vacío"
        !isValidEmail(email) -> "El correo no es válido"
        password.isEmpty() -> "La contraseña no puede estar vacía"
        confirmPassword.isEmpty() -> "La confirmación de contraseña no puede estar vacía"
        password != confirmPassword -> "Las contraseñas no coinciden"
        else -> null
    }
}