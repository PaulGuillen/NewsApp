package com.devpaul.infoxperu.domain.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.feature.util.ui.utils.CustomDialog

@Composable
fun ShowDialogSuccessRegister(
    onClickAction: () -> Unit
) {
    CustomDialog(
        image = painterResource(id = R.drawable.baseline_check_circle_24),
        title = stringResource(id = R.string.register_success),
        description = null,
        buttonText = stringResource(id = R.string.accept),
        onButtonClick = onClickAction
    )
}

@Preview
@Composable
fun ShowDialogSuccessRegisterPreview() {
    ShowDialogSuccessRegister {}
}