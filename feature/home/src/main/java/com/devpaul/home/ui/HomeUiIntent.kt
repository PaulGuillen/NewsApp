package com.devpaul.home.ui

sealed class HomeUiIntent {
    data object GetDollarQuote : HomeUiIntent()
    data object GetUIT : HomeUiIntent()
    data object GetGratitude : HomeUiIntent()
    data object GetSection : HomeUiIntent()
}