package com.devpaul.infoxperu.feature.user_start.login.components.molecules

import androidx.compose.runtime.Composable
import com.devpaul.infoxperu.feature.user_start.login.components.atoms.CustomTextField
import com.devpaul.infoxperu.feature.user_start.login.components.atoms.TextFieldConfig
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.infoxperu.R

@Composable
fun EmailField(value: String, onValueChange: (String) -> Unit) {
    CustomTextField(
        value = value,
        onValueChange = onValueChange,
        config = TextFieldConfig(
            label = "Email",
            leadingIcon = {
                androidx.compose.material3.Icon(
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