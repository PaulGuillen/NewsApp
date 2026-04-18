package com.devpaul.shared.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun uriToBitmap(context: Context, uri: Uri): Bitmap {
    val inputStream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream!!)
}

fun bitmapToBase64(bitmap: Bitmap, quality: Int = 40): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.NO_WRAP)
}

fun base64ToImageBitmap(base64Str: String): ImageBitmap? {
    return try {
        val decodedBytes = Base64.decode(base64Str, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        bitmap.asImageBitmap()
    } catch (e: Exception) {
        null
    }
}

fun formatRelativeTime(seconds: Long, nanoseconds: Int): String {
    val dateTime = Instant.ofEpochSecond(seconds, nanoseconds.toLong())
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

    val now = LocalDateTime.now()

    val minutes = ChronoUnit.MINUTES.between(dateTime, now)
    val hours = ChronoUnit.HOURS.between(dateTime, now)
    val days = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate())

    return when {
        minutes < 1 -> "Justo ahora"
        minutes == 1L -> "Hace 1 minuto"
        minutes < 60 -> "Hace $minutes minutos"
        hours == 1L -> "Hace 1 hora"
        hours < 24 -> "Hace $hours horas"
        days == 1L -> "Hace 1 día"
        else -> "Hace $days días"
    }
}

fun formatTime(date: String?): String {
    return "15 min"
}

fun formatDeltaTime(date: Any?): String {

    return when (date) {

        is String -> {
            try {
                // 🔥 Convertir a formato ISO válido
                val formatted = date.replace(
                    Regex("(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})Z"),
                    "$1-$2-$3T$4:$5:$6Z"
                )

                val instant = Instant.parse(formatted)

                getRelativeTime(instant.toEpochMilli())

            } catch (e: Exception) {
                ""
            }
        }

        is Long -> getRelativeTime(date)

        else -> ""
    }
}

fun formatGoogleTime(pubDate: String?): String {
    if (pubDate.isNullOrEmpty()) return ""

    return try {
        val zonedDateTime = ZonedDateTime.parse(
            pubDate,
            DateTimeFormatter.RFC_1123_DATE_TIME
        )

        val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())

        val timestamp = localDateTime.toInstant().toEpochMilli()

        getRelativeTime(timestamp)

    } catch (e: Exception) {
        ""
    }
}

fun formatRedditTime(timestamp: Long?): String {
    return timestamp?.let { getRelativeTime(it) } ?: ""
}

fun getRelativeTime(timestamp: Long): String {

    val now = System.currentTimeMillis()
    val diff = now - timestamp

    val minutes = diff / (1000 * 60)
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> "Ahora"
        minutes == 1L -> "Hace 1 minuto"
        minutes < 60 -> "Hace $minutes min"
        hours == 1L -> "Hace 1 hora"
        hours < 24 -> "Hace $hours h"
        days == 1L -> "Hace 1 día"
        else -> "Hace $days d"
    }
}

