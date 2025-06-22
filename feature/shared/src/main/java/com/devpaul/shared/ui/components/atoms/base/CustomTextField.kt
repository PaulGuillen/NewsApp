package com.devpaul.shared.ui.components.atoms.base

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    config: TextFieldConfig
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(config.label) },
        leadingIcon = config.leadingIcon,
        trailingIcon = config.trailingIcon,
        visualTransformation = config.visualTransformation,
        keyboardOptions = config.keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    CustomTextField(
        value = "",
        onValueChange = {},
        config = TextFieldConfig(
            label = "Email",
            leadingIcon = { /*Icon(Icons.Filled.Lock, contentDescription = null)*/ },
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default
        )
    )
}