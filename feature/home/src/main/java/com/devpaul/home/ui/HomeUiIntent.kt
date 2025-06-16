package com.devpaul.home.ui

sealed class HomeUiIntent {
    data object GetHomeServices : HomeUiIntent()
}