package com.devpaul.shared.ui.components.molecules

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun PhoneOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    leadingIcon: ImageVector = Icons.Default.Phone,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next),
    maxLength: Int = Int.MAX_VALUE
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            if (it.length <= maxLength) onValueChange(it)
        },
        label = { Text(stringResource(id = labelRes)) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null) },
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed,
        )
    )
}