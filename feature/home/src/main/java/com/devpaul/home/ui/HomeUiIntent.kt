package com.devpaul.home.ui

sealed class HomeUiIntent {
    data object GetHomeSection : HomeUiIntent()
}