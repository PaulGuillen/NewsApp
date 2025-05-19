package com.devpaul.shared.components.molecules

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.BrickRed

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (passwordVisible) None else PasswordVisualTransformation(),
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
        trailingIcon = {
            val icon = if (passwordVisible)
                painterResource(id = R.drawable.baseline_visibility_24)
            else
                painterResource(id = R.drawable.baseline_visibility_off_24)

            IconButton(onClick = onPasswordVisibilityChange) {
                Icon(painter = icon, contentDescription = null)
            }
        },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BrickRed,
            cursorColor = BrickRed,
            focusedLabelColor = BrickRed,
        )
    )
}

@Preview(showBackground = true, name = "Password Text Field Preview")
@Composable
fun PreviewPasswordTextField() {
    PasswordField(
        value = "password",
        onValueChange = {},
        label = "Password",
        passwordVisible = true,
        onPasswordVisibilityChange = {},
    )
}