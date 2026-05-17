package com.devpaul.core_platform.extension

import android.content.Context
import android.util.Patterns
import com.devpaul.core_platform.R
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
}

fun validateRegistration(
    context: Context,
    name: String,
    lastName: String,
    email: String,
    password: String,
    confirmPassword: String
): String? {

    val cleanName = name.trim()
    val cleanLastName = lastName.trim()
    val cleanEmail = email.trim()

    return when {

        cleanName.isEmpty() ->
            context.getString(R.string.error_name_empty)

        cleanName.length < 2 ->
            context.getString(R.string.error_name_invalid)

        cleanLastName.isEmpty() ->
            context.getString(R.string.error_lastname_empty)

        cleanLastName.length < 2 ->
            context.getString(R.string.error_lastname_invalid)

        cleanEmail.isEmpty() ->
            context.getString(R.string.error_email_empty)

        !isValidEmail(cleanEmail) ->
            context.getString(R.string.error_email_invalid)

        password.isEmpty() ->
            context.getString(R.string.error_password_empty)

        password.length < 6 ->
            context.getString(R.string.error_password_length)

        confirmPassword.isEmpty() ->
            context.getString(R.string.error_confirm_password_empty)

        password != confirmPassword ->
            context.getString(R.string.error_password_mismatch)

        else -> null
    }
}

fun validateEmail(
    context: Context,
    email: String,
): String? {

    val cleanEmail = email.trim()

    return when {

        cleanEmail.isEmpty() ->
            context.getString(R.string.error_email_empty)

        !isValidEmail(cleanEmail) ->
            context.getString(R.string.error_email_invalid)

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
    if (text.length > maxCharacters) {
        return text.take(maxCharacters) + "…"
    }

    return text
}

fun Long.toDateText(
    zoneId: ZoneId = ZoneId.of("America/Lima"),
    pattern: String = "dd 'de' MMMM yyyy, HH:mm"
): String {
    return Instant.ofEpochMilli(this)
        .atZone(zoneId)
        .format(
            DateTimeFormatter
                .ofPattern(pattern)
                .withLocale(Locale("es", "PE"))
        )
}

fun String.toReadableDate(
    zoneId: ZoneId = ZoneId.of("America/Lima"),
    pattern: String = "dd 'de' MMMM yyyy, HH:mm"
): String {
    val millis = this.toEpochMillis()
    if (millis == 0L) return ""

    return Instant.ofEpochMilli(millis)
        .atZone(zoneId)
        .format(
            DateTimeFormatter
                .ofPattern(pattern)
                .withLocale(Locale("es", "PE"))
        )
}

fun String.toEpochMillis(): Long {
    if (this.isBlank()) return 0L

    return try {
        when {
            this.contains("T") && this.endsWith("Z") -> {
                ZonedDateTime.parse(
                    this,
                    DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX")
                ).toInstant().toEpochMilli()
            }
            this.length <= 10 -> this.toLong() * 1000
            else -> this.toLong()
        }
    } catch (e: Exception) {
        0L
    }
}