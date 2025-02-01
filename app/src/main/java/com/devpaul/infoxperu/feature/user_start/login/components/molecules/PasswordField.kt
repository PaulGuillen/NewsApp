package com.devpaul.infoxperu.feature.user_start.login.components.molecules

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.IconButton
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.infoxperu.feature.util.atoms.CustomTextField
import com.devpaul.infoxperu.feature.util.atoms.TextFieldConfig
import com.devpaul.infoxperu.R

@Composable
fun PasswordField(value: String, onValueChange: (String) -> Unit) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        config = TextFieldConfig(
            label = "Password",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    val icon = if (isPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.baseline_visibility_off_24
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null
                    )
                }
            },
            visualTransformation = if (isPasswordVisible) None
            else PasswordVisualTransformation()
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordFieldPreview() {
    PasswordField(value = "") {}
}