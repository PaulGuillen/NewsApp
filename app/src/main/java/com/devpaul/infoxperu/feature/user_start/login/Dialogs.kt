package com.devpaul.infoxperu.feature.user_start.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.devpaul.infoxperu.R
import com.devpaul.infoxperu.domain.ui.utils.CustomDialog

object Dialogs {

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

}