package com.devpaul.auth.ui.login.components

import com.devpaul.core_data.viewmodel.StatelessViewModel
import org.koin.android.annotation.KoinViewModel
import timber.log.Timber

@KoinViewModel
class LoginViewModel() : StatelessViewModel<LoginUiEvent, LoginUiIntent>() {

    init {
        Timber.d("LoginViewModel initialized")
    }
}