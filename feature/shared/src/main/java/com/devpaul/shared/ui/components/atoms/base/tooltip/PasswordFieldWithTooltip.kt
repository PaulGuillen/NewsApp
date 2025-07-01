package com.devpaul.shared.ui.components.atoms.base.tooltip

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
import androidx.compose.ui.unit.dp
import com.devpaul.shared.ui.components.atoms.base.textfield.PasswordField
import kotlinx.coroutines.delay

@Composable
fun PasswordFieldWithTooltip(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    tooltipMessage: String = "",
    tooltipDuration: Long = 2000L,
    enabled: Boolean = true,
) {
    var showTooltip by remember { mutableStateOf(false) }

    LaunchedEffect(showTooltip) {
        if (showTooltip) {
            delay(tooltipDuration)
            showTooltip = false
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
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

        PasswordField(
            value = value,
            onValueChange = onValueChange,
            label = label,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChange,
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!enabled && tooltipMessage.isNotEmpty()) {
                        Modifier.clickable {
                            showTooltip = true
                        }
                    } else Modifier
                )
        )
    }
}
