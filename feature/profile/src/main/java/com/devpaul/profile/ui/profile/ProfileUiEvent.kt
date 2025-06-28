package com.devpaul.profile.ui.profile

import android.content.Intent

sealed class ProfileUiEvent {
    data class LaunchIntent(val intent: Intent) : ProfileUiEvent()
    data object UserLoggedOut : ProfileUiEvent()
    data object GoToEditProfile : ProfileUiEvent()
}