package com.devpaul.home.ui.home

sealed class HomeUiIntent {
    data object GetHomeServices : HomeUiIntent()
    data object GetDollarQuote : HomeUiIntent()
    data object GetUITValue : HomeUiIntent()
}