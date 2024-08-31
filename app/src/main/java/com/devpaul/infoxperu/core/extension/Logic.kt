package com.devpaul.infoxperu.core.extension

import android.content.Context
import com.devpaul.infoxperu.R
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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

fun formatPubDate(dateStr: String?): String {
    return try {
        val parser = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd 'de' MMMM, hh:mm a", Locale("es", "ES"))
        val parsedDate = parser.parse(dateStr.toString())
        formatter.format(parsedDate ?: Date())
    } catch (e: Exception) {
        dateStr ?: ""
    }
}

fun formatIsoDate(isoDate: String): String {
    val inputFormat = SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'", Locale.US)
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date? = inputFormat.parse(isoDate)
    val outputFormat = SimpleDateFormat("dd 'de' MMMM, hh:mm a", Locale("es", "ES"))
    outputFormat.timeZone = TimeZone.getDefault()
    return if (date != null) {
        outputFormat.format(date)
    } else {
        "Formato no soportado"
    }
}

fun limitText(text: String, maxCharacters: Int): String {
    return if (text.length > maxCharacters) {
        text.take(maxCharacters) + "…"
    } else {
        text
    }
}

fun formatPublishedAt(publishedAt: String): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(publishedAt)
        val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, h:mm a", Locale("es", "ES"))
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        ""
    }
}