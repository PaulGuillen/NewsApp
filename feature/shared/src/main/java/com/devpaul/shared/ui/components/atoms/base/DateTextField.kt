package com.devpaul.shared.ui.components.atoms.base

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.devpaul.core_platform.theme.BrickRed
import java.util.Calendar

@SuppressLint("DefaultLocale")
@Composable
fun DateTextField(
    labelRes: Int,
    value: String,
    onDateSelected: (String) -> Unit,
    leadingIcon: ImageVector = Icons.Default.DateRange,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val datePickerDialog = remember {
        android.app.DatePickerDialog(
            context,
            { _, year, month, day ->
                val date = String.format("%02d/%02d/%04d", day, month + 1, year)
                onDateSelected(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    OutlinedTextField(
        value = value,
        onValueChange = {},
        modifier = Modifier
            .fillMaxWidth()
            .clickable { datePickerDialog.show() },
        label = { Text(stringResource(id = labelRes)) },
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = null)
        },
        enabled = false,
        readOnly = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed,
        )
    )
}