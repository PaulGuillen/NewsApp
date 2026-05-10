package com.devpaul.shared.ui.components.atoms.base.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    elevation: Dp = 8.dp,
    vectorIcon: ImageVector? = null,
    painterIcon: Painter? = null,
    contentDescription: String? = null,
    containerColor: Color = BrickRed,
    contentColor: Color = White,
    glowColor: Color = containerColor,
    shadowEnabled: Boolean = false,
    borderColor: Color = Color.Transparent,
) {
    val isDark = isSystemInDarkTheme()
    val shape = RoundedCornerShape(12.dp)

    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = if (shadowEnabled && isDark) 14.dp else 0.dp,
                shape = shape,
                ambientColor = glowColor,
                spotColor = glowColor
            )
            .fillMaxWidth()
            .height(48.dp),
        shape = shape,
        border = BorderStroke(1.dp, borderColor),
        elevation = ButtonDefaults.buttonElevation(elevation),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        when {
            painterIcon != null -> {
                Image(
                    painter = painterIcon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(20.dp)
                )
            }

            vectorIcon != null -> {
                Icon(
                    imageVector = vectorIcon,
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .padding(end = 8.dp)
                )
            }
        }

        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    CustomButton(
        text = "Iniciar sesión",
        onClick = {}
    )
}