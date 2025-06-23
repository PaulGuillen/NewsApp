package com.devpaul.shared.ui.components.atoms.base.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SuccessNotification(
    visible: Boolean,
    titleHeader: String = "Success dialog",
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
    val scope = rememberCoroutineScope()

    LaunchedEffect(visible) {
        if (visible) animateVisible = true
    }

    AnimatedVisibility(
        visible = visible && animateVisible,
        enter = slideInVertically(
            initialOffsetY = { -it },
            animationSpec = tween(500)
        ) + fadeIn(animationSpec = tween(500)),
        exit = slideOutVertically(
            targetOffsetY = { -it },
            animationSpec = tween(400)
        ) + fadeOut(animationSpec = tween(400))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
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
                            .background(Color(0xFFDFF6DD))
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = titleHeader,
                            color = Color(0xFF137333),
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
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF137333),
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(48.dp)
                        )

                        Column {
                            Text(
                                text = title,
                                color = Color(0xFF137333),
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
                                scope.launch {
                                    animateVisible = false
                                    delay(400)
                                    onPrimaryClick()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF137333),
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
fun NotificationPreview() {
    SuccessNotification(
        visible = true,
        titleHeader = "Positive dialog",
        title = "Successful purchase!",
        message = "Do you know Material X system contains material design components re-styled as it should look and behave for today.",
        primaryButtonText = "Primary Action",
        secondaryButtonText = "Secondary",
        onPrimaryClick = {},
        onSecondaryClick = {},
        onDismiss = {}
    )
}