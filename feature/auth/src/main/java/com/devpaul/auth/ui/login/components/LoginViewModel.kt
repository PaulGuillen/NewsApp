package com.devpaul.auth.ui.login.components

import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import timber.log.Timber

@KoinViewModel
class LoginViewModel() : ViewModel() {

    init {
        Timber.d("LoginViewModel initialized")
    }
}