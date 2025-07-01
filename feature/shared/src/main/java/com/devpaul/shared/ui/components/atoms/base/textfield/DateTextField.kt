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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateTextField(
    @StringRes labelRes: Int,
    value: String,
    onDateSelected: (String) -> Unit,
    enabled: Boolean = true,
    tooltipMessage: String = "",
    tooltipDuration: Long = 3000L,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val date = remember { mutableStateOf(LocalDate.now()) }

    val dialogState = rememberMaterialDialogState()
    var showTooltip by remember { mutableStateOf(false) }

    LaunchedEffect(showTooltip) {
        if (showTooltip) {
            delay(tooltipDuration)
            showTooltip = false
        }
    }

    MaterialDialog(
        dialogState = dialogState,
        backgroundColor = White,
        buttons = {
            positiveButton(text = "OK", textStyle = TextStyle(color = BrickRed))
            negativeButton(text = "Cancelar", textStyle = TextStyle(color = BrickRed))
        }
    ) {
        datepicker(
            initialDate = date.value,
            title = "Selecciona una fecha",
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = BrickRed,
                dateActiveBackgroundColor = BrickRed,
                dateInactiveTextColor = BrickRed.copy(alpha = 0.5f),
            ),
            allowedDateValidator = { it.year <= LocalDate.now().year }
        ) {
            date.value = it
            onDateSelected(it.format(formatter))
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
                .offset(x = (-8).dp, y = (-36).dp)
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

        OutlinedTextField(
            value = value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showTooltip = true
                    }
                },
            label = { Text(stringResource(id = labelRes)) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        dialogState.show()
                    },
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = BrickRed
                )
            },
            enabled = enabled,
            readOnly = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BrickRed,
                cursorColor = BrickRed,
                focusedLabelColor = BrickRed,
            )
        )
    }
}