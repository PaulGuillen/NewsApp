package com.devpaul.emergency.ui.emergency.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.Black
import com.devpaul.core_platform.theme.White
import androidx.compose.ui.graphics.Color
@Composable
fun ServiceCallButton(
    onClick: () -> Unit,
    dayColor: Color,
    nightColor: Color
) {
    val isDark = isSystemInDarkTheme()

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(36.dp)
            .widthIn(max = 110.dp),
        shape = RoundedCornerShape(20.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isDark) nightColor else dayColor,
            contentColor = if (isDark) Black else White
        )
    ) {
        Icon(
            Icons.Default.Phone,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text("Llamar")
    }
}