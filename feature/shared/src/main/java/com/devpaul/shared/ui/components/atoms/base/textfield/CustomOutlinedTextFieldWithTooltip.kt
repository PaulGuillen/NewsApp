package com.devpaul.shared.ui.components.atoms.base.textfield

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CustomOutlinedTextFieldWithTooltip(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    leadingIcon: ImageVector = Icons.Default.Person,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    enabled: Boolean = true,
    tooltipMessage: String = "",
    tooltipDuration: Long = 2000L,
) {
    var showTooltip by remember { mutableStateOf(false) }

    LaunchedEffect(showTooltip) {
        if (showTooltip) {
            delay(tooltipDuration)
            showTooltip = false
        }
    }

    Box(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            visible = showTooltip,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(800)
            ) + fadeIn(tween(800)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(600)
            ) + fadeOut(animationSpec = tween(600)),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(y = (-36).dp, x = (-8).dp)
        ) {
            Box(
                modifier = Modifier
                    .background(Color(0xFF333333), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = tooltipMessage,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        CustomOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!enabled && tooltipMessage.isNotEmpty()) {
                        Modifier.clickable {
                            showTooltip = true
                        }
                    } else Modifier
                ),
            value = value,
            onValueChange = onValueChange,
            labelRes = labelRes,
            leadingIcon = leadingIcon,
            keyboardOptions = keyboardOptions,
            enabled = enabled,
        )
    }
}
