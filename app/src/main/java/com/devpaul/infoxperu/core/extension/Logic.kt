package com.devpaul.infoxperu.core.extension

import android.content.Context
import com.devpaul.infoxperu.R

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validateRegistration(
    context: Context,
    name: String,
    lastName: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {
    return when {
        name.isEmpty() -> context.getString(R.string.error_name_empty)
        lastName.isEmpty() -> context.getString(R.string.error_lastname_empty)
        email.isEmpty() -> context.getString(R.string.error_email_empty)
        !isValidEmail(email) -> context.getString(R.string.error_email_invalid)
        password.isEmpty() -> context.getString(R.string.error_password_empty)
        confirmPassword.isEmpty() -> context.getString(R.string.error_confirm_password_empty)
        password != confirmPassword -> context.getString(R.string.error_password_mismatch)
        else -> null
    }
}