package com.devpaul.shared.ui.components.atoms.base

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.devpaul.core_platform.theme.BrickRed
import com.devpaul.core_platform.theme.White
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateTextField(
    labelRes: Int,
    value: String,
    onDateSelected: (String) -> Unit
) {
    val formatter = remember { DateTimeFormatter.ofPattern("dd/MM/yyyy") }
    val date = remember { mutableStateOf(LocalDate.now()) }

    val dialogState = rememberMaterialDialogState()

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
            allowedDateValidator = {
                it.year < LocalDate.now().year
            }
        ) {
            date.value = it
            onDateSelected(it.format(formatter))
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable { dialogState.show() },
        label = { Text(stringResource(id = labelRes)) },
        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
        enabled = false,
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed,
        )
    )
}