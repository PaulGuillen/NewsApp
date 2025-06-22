package com.devpaul.core_platform.extension

import android.content.Context
import android.util.Patterns
import com.devpaul.core_platform.R

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}

fun validateRegistration(
    context: Context,
    name: String,
    lastName: String,
    phone: String,
    email: String,
    birthdate: String,
    password: String,
    confirmPassword: String
): String? {
    return when {
        name.isEmpty() -> context.getString(R.string.error_name_empty)
        lastName.isEmpty() -> context.getString(R.string.error_lastname_empty)
        phone.isEmpty() -> context.getString(R.string.error_phone_empty)
        email.isEmpty() -> context.getString(R.string.error_email_empty)
        !isValidEmail(email) -> context.getString(R.string.error_email_invalid)
        birthdate.isEmpty() -> context.getString(R.string.error_birthdate_empty)
        password.isEmpty() -> context.getString(R.string.error_password_empty)
        confirmPassword.isEmpty() -> context.getString(R.string.error_confirm_password_empty)
        password != confirmPassword -> context.getString(R.string.error_password_mismatch)
        else -> null
    }
}

fun validateEmail(
    context: Context,
    email: String,
): String? {
    return when {
        email.isEmpty() -> context.getString(R.string.error_email_empty)
        !isValidEmail(email) -> context.getString(R.string.error_email_invalid)
        else -> null
    }
}

fun validateStartSession(
    context: Context,
    email: String,
    password: String,
): String? {
    return when {
        email.isEmpty() -> context.getString(R.string.error_email_empty)
        !isValidEmail(email) -> context.getString(R.string.error_email_invalid)
        password.isEmpty() -> context.getString(R.string.error_password_empty)
        else -> null
    }
}

fun limitText(text: String, maxCharacters: Int): String {
    return if (text.length > maxCharacters) {
        text.take(maxCharacters) + "…"
    } else {
        text
    }
}