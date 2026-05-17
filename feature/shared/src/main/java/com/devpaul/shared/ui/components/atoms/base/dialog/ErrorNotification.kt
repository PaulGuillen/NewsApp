package com.devpaul.shared.ui.components.atoms.base.dialog

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.devpaul.core_platform.theme.ErrorButtonText
import com.devpaul.core_platform.theme.InfoXPeruTheme

@Composable
fun ErrorNotification(
    visible: Boolean,
    titleHeader: String = "Error dialog",
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
                    initialOffsetY = { -it / 3 },
                    animationSpec = tween(500)
                ) + fadeIn(tween(400)),
                exit = slideOutVertically(
                    targetOffsetY = { -it / 4 },
                    animationSpec = tween(280)
                ) + fadeOut(tween(220))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorNotificationContent(
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
}

@Composable
fun ErrorNotificationContent(
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
    NotificationContent(
        titleHeader = titleHeader,
        title = title,
        message = message,
        icon = Icons.Default.Clear,
        accent = ErrorButtonText,
        primaryButtonText = primaryButtonText,
        onPrimaryClick = onPrimaryClick,
        onDismiss = onDismiss,
        secondaryButtonText = secondaryButtonText,
        onSecondaryClick = onSecondaryClick,
        showDismissIcon = showDismissIcon
    )
}

@Preview(
    name = "Error - Light",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Error - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ErrorNotificationPreview() {
    InfoXPeruTheme(darkTheme = isSystemInDarkTheme(), dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            contentAlignment = Alignment.Center
        ) {
            ErrorNotificationContent(
                titleHeader = "Atencion",
                title = "Actualizacion fallida",
                message = "No pudimos guardar tus cambios en este momento. Intentalo nuevamente en unos segundos.",
                primaryButtonText = "Reintentar",
                secondaryButtonText = "Cancelar",
                onPrimaryClick = {},
                onSecondaryClick = {},
                onDismiss = {}
            )
        }
    }
}
