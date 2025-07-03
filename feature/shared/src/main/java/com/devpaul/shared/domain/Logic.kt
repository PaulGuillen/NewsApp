package com.devpaul.shared.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.ByteArrayOutputStream

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