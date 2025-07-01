package com.devpaul.shared.ui.components.atoms.base.textfield

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelRes: Int,
    leadingIcon: ImageVector = Icons.Default.Person,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    enabled : Boolean = true,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = labelRes)) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null) },
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed,
        )
    )
}

@Preview(showBackground = true, name = "Custom Outlined TextField Preview")
@Composable
fun PreviewCustomOutlinedTextField() {
    MaterialTheme {
        CustomOutlinedTextField(
            value = "Guillen",
            onValueChange = {},
            labelRes = R.string.register_screen_last_name,
            leadingIcon = Icons.Default.Person,
        )
    }
}