package com.devpaul.shared.ui.components.atoms.base.button

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.White

@Composable
fun OutlinedLoadMoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "Cargar más",
    @DrawableRes iconResId: Int = R.drawable.baseline_arrow_drop_down_circle_24,
    iconTint: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.secondary,
    showIcon: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = White,
            contentColor = textColor
        ),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, textColor),
        elevation = ButtonDefaults.buttonElevation(4.dp)
    ) {
        if (showIcon) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(20.dp),
                tint = iconTint
            )
        }
        Text(
            text = text,
            modifier = Modifier.padding(vertical = 8.dp),
            color = textColor,
            fontSize = 16.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun OutlinedLoadMoreButtonPreview() {
    OutlinedLoadMoreButton(onClick = {})
}