package com.devpaul.shared.ui.components.atoms.base.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devpaul.core_platform.theme.ColorHorizontalDivider
import com.devpaul.core_platform.theme.InfoButtonText
import com.devpaul.core_platform.theme.InfoIcon
import com.devpaul.core_platform.theme.InfoTitle
import com.devpaul.core_platform.theme.InfoTitleHeader
import com.devpaul.core_platform.theme.InfoTitleHeaderBackground

@Composable
fun InfoNotification(
    visible: Boolean,
    titleHeader: String = "Notificación",
    title: String,
    message: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    showDismissIcon: Boolean = true
) {

    var animateVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        animateVisible = visible
    }

    if (visible) {
        Dialog(onDismissRequest = { onDismiss?.invoke() }) {
            AnimatedVisibility(
                visible = animateVisible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(800)
                ) + fadeIn(tween(800)),

                exit = slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(600)
                ) + fadeOut(tween(600))
            ) {
                InfoNotificationContent(
                    titleHeader = titleHeader,
                    title = title,
                    message = message,
                    primaryButtonText = primaryButtonText,
                    onPrimaryClick = onPrimaryClick,
                    onDismiss = onDismiss,
                    secondaryButtonText = secondaryButtonText,
                    onSecondaryClick = onSecondaryClick,
                    showDismissIcon = showDismissIcon
                )
            }
        }
    }
}

@Composable
fun InfoNotificationContent(
    titleHeader: String,
    title: String,
    message: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onDismiss: (() -> Unit)? = null,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    showDismissIcon: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(InfoTitleHeaderBackground)
                        .padding(start = 16.dp, end = 4.dp, top = 8.dp, bottom = 8.dp),
                ) {
                    Text(
                        text = titleHeader,
                        color = InfoTitleHeader,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                    if (showDismissIcon) {
                        IconButton(
                            onClick = { onDismiss?.invoke() },
                            modifier = Modifier.align(Alignment.CenterEnd)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Cerrar")
                        }
                    } else {
                        Spacer(modifier = Modifier.size(38.dp))
                    }
                }

                HorizontalDivider(color = ColorHorizontalDivider)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = InfoIcon,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(48.dp)
                    )

                    Column {
                        Text(
                            text = title,
                            color = InfoTitle,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black.copy(alpha = 0.87f)
                        )
                    }
                }

                HorizontalDivider(color = ColorHorizontalDivider)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (!secondaryButtonText.isNullOrBlank() && onSecondaryClick != null) {
                        OutlinedButton(
                            onClick = {
                                onSecondaryClick()
                                onDismiss?.invoke()
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
                        ) {
                            Text(secondaryButtonText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = {
                            onPrimaryClick()
                            onDismiss?.invoke()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = InfoButtonText,
                            contentColor = Color.White
                        )
                    ) {
                        Text(primaryButtonText)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoNotificationPreview() {
    InfoNotificationContent(
        titleHeader = "Info dialog",
        title = "Schedule an event",
        message = "Do you know Material X system contains material design components re-styled as it should look and behave for today.",
        primaryButtonText = "Primary Action",
        secondaryButtonText = "Secondary",
        onPrimaryClick = {},
        onSecondaryClick = {},
        onDismiss = {}
    )
}