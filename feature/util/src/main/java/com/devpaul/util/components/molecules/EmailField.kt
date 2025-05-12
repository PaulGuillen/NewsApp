package com.devpaul.util.components.molecules

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.core_platform.R
import com.devpaul.util.components.atoms.CustomTextField
import com.devpaul.util.components.atoms.TextFieldConfig

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        config = TextFieldConfig(
            label = "Email",
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_email_24),
                    contentDescription = "Email Icon"
                )
            }
        )
    )
}

@Preview(showBackground = true)
@Composable
fun EmailFieldPreview() {
    EmailField(value = "") {}
}