package com.devpaul.home.ui

sealed class HomeUiIntent {
    data object GetHomeServices : HomeUiIntent()
    data object GetGratitudeServices : HomeUiIntent()
    data object GetSections : HomeUiIntent()
}