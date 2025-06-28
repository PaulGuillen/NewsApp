package com.devpaul.profile.ui.profile

sealed class ProfileUiIntent {
    data object GetUserProfile : ProfileUiIntent()
    data object ShareApp : ProfileUiIntent()
    data object OpenTerms : ProfileUiIntent()
    data object OpenPrivacy : ProfileUiIntent()
    data object Logout : ProfileUiIntent()
}