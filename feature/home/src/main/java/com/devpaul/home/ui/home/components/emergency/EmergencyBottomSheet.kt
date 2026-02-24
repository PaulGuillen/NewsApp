package com.devpaul.home.ui.home.components.emergency

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.theme.InfoXPeruTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmergencyBottomSheet(
    onDismiss: () -> Unit,
    onCall: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    ) {
        EmergencyBottomSheetContent(
            onDismiss = onDismiss,
            onCall = onCall
        )
    }
}

@Composable
private fun EmergencyBottomSheetContent(
    onDismiss: () -> Unit,
    onCall: (String) -> Unit
) {

    val isDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Llamada de Emergencia",
                style = MaterialTheme.typography.titleMedium,
                color = if (isDark) Color.White else Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        EmergencyCallItem(
            title = "Policía Nacional",
            subtitle = "Central de Emergencias 105",
            iconColor = Color(0xFF2563EB),
            isDark = isDark,
            onClick = { onCall("105") }
        )

        EmergencyCallItem(
            title = "Cuerpo de Bomberos",
            subtitle = "Emergencias 116",
            iconColor = Color(0xFFDC2626),
            isDark = isDark,
            onClick = { onCall("116") }
        )

        EmergencyCallItem(
            title = "SAMU",
            subtitle = "Atención Médica 106",
            iconColor = Color(0xFF10B981),
            isDark = isDark,
            onClick = { onCall("106") }
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onDismiss,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                "CANCELAR",
                letterSpacing = 2.sp,
                color = if (isDark) Color.Gray else Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(
    name = "Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun EmergencyBottomSheetContentPreviewLight() {
    InfoXPeruTheme(
        darkTheme = false,
        dynamicColor = false
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            EmergencyBottomSheetContent(
                onDismiss = {},
                onCall = {}
            )
        }
    }
}

@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun EmergencyBottomSheetContentPreviewDark() {
    InfoXPeruTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            EmergencyBottomSheetContent(
                onDismiss = {},
                onCall = {}
            )
        }
    }
}