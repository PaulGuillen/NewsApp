package com.devpaul.shared.ui.components.atoms.base.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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

@Composable
fun InfoNotification(
    visible: Boolean,
    titleHeader: String = "Info dialog",
    title: String,
    message: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onDismiss: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    showDismissIcon: Boolean = true
) {
    var animateVisible by remember { mutableStateOf(false) }

    LaunchedEffect(visible) {
        if (visible) {
            animateVisible = true
        }
    }

    if (visible) {
        InfoNotificationContent(
            animateVisible = animateVisible,
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

@Composable
fun InfoNotificationContent(
    animateVisible: Boolean,
    titleHeader: String,
    title: String,
    message: String,
    primaryButtonText: String,
    onPrimaryClick: () -> Unit,
    onDismiss: () -> Unit,
    secondaryButtonText: String? = null,
    onSecondaryClick: (() -> Unit)? = null,
    showDismissIcon: Boolean = true
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = animateVisible,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(500)
            ) + fadeIn(tween(500)),

            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(400)
            ) + fadeOut(tween(400))
        ) {
            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFE0F2FE))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = titleHeader,
                            color = Color(0xFF2563EB),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                        if (showDismissIcon) {
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            ) {
                                Icon(Icons.Default.Close, contentDescription = "Cerrar")
                            }
                        }
                    }

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF9CA3AF),
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(48.dp)
                        )

                        Column {
                            Text(
                                text = title,
                                color = Color(0xFF1E3A8A),
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

                    HorizontalDivider(color = Color(0xFFE0E0E0))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        if (!secondaryButtonText.isNullOrBlank() && onSecondaryClick != null) {
                            OutlinedButton(
                                onClick = {
                                    onSecondaryClick()
                                    onDismiss()
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
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF2563EB),
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
}

@Preview(showBackground = true)
@Composable
fun InfoNotificationPreview() {
    InfoNotificationContent(
        animateVisible = true,
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