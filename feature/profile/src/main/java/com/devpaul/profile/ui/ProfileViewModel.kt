package com.devpaul.profile.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel : StatefulViewModel<ProfileUiState, ProfileUiIntent, ProfileUiEvent>(
    defaultUIState = {
        ProfileUiState()
    }
) {
    init {

    }

    override suspend fun onUiIntent(intent: ProfileUiIntent) {

    }

}