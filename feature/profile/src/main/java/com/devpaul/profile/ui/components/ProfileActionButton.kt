package com.devpaul.profile.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun ProfileActionButton(
    text: String,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier
) {
    val colors = rememberProfileUiColors()
    val isDark = isSystemInDarkTheme()
    val shape = RoundedCornerShape(14.dp)

    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = if (isDark) 12.dp else 0.dp,
                shape = shape,
                ambientColor = colors.actionBorder,
                spotColor = colors.actionBorder
            )
            .fillMaxWidth()
            .height(52.dp),
        shape = shape,
        border = BorderStroke(if (isDark) 1.4.dp else 1.dp, colors.actionBorder),
        colors = ButtonDefaults.buttonColors(
            containerColor = colors.actionContainer,
            contentColor = colors.actionContent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp
        )
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = colors.actionContent
        )
    }
}
