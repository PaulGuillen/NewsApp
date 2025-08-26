package com.devpaul.shared.domain

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.net.toUri

/**
 * Uso:
 * val performCall = rememberCallPerformer(
 *     onDenied = { permanently -> /* snackbar / dialog */ }
 * )
 * performCall("+51988888888", direct = true) // pide permiso y llama
 * performCall("105", direct = false)         // solo abre el dialer (sin permiso)
 */

// CallPermissionHelper.kt
data class CallActions(
    val callDirect: (String) -> Unit,   // ACTION_CALL (pide permiso)
    val openDialer: (String) -> Unit    // ACTION_DIAL (sin permiso)
)

@Composable
fun rememberCallActions(
    onDenied: (Boolean) -> Unit = {}
): CallActions {
    val performer = rememberCallPerformer(onDenied) // tu helper actual
    return CallActions(
        callDirect = { phone -> performer(phone, true) },
        openDialer = { phone -> performer(phone, false) }
    )
}


@Composable
fun rememberCallPerformer(
    onDenied: (permanentlyDenied: Boolean) -> Unit = {}
): (phone: String, direct: Boolean) -> Unit {
    val context = androidx.compose.ui.platform.LocalContext.current
    var pendingPhone by remember { mutableStateOf<String?>(null) }
    var pendingDirect by remember { mutableStateOf(false) }

    // Request single permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        val phone = pendingPhone
        val direct = pendingDirect
        if (granted && phone != null) {
            startCall(context, phone, direct)
        } else {
            // Si no se concedió, notifica
            onDenied(false) // si quieres detectar "permanentlyDenied", ver nota abajo
        }
        pendingPhone = null
        pendingDirect = false
    }

    return remember {
        { phone: String, direct: Boolean ->
            val clean = phone.filter { it.isDigit() || it == '+' }
            if (!direct) {
                // ACTION_DIAL no requiere permiso
                startCall(context, clean, false)
            } else {
                // ACTION_CALL requiere permiso
                val granted = androidx.core.content.ContextCompat.checkSelfPermission(
                    context, Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED

                if (granted) {
                    startCall(context, clean, true)
                } else {
                    pendingPhone = clean
                    pendingDirect = true
                    permissionLauncher.launch(Manifest.permission.CALL_PHONE)
                }
            }
        }
    }
}

private fun startCall(context: Context, phone: String, direct: Boolean) {
    val intent = if (direct) {
        Intent(Intent.ACTION_CALL).apply { data = "tel:$phone".toUri() }
    } else {
        Intent(Intent.ACTION_DIAL).apply { data = Uri.parse("tel:$phone") }
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "No se pudo abrir el marcador", Toast.LENGTH_SHORT).show()
    }
}

private val phoneRegex = Regex("""\+?\d[\d\s\-]+""") // 2+ chars tipo teléfono

/** Extrae teléfonos de un string (soporta saltos de línea, comas, etc.) */
fun extractPhones(raw: String): List<String> {
    // separadores comunes + saltos de línea
    val chunks = raw.split('\n', ',', ';', '/')
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    val phones = chunks.flatMap { line ->
        phoneRegex.findAll(line).map { m ->
            // normaliza espacios múltiples
            m.value.replace(Regex("\\s+"), " ").trim()
        }.toList()
    }

    return phones.distinct()
}
