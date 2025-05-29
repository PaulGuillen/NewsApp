package com.devpaul.home.ui.home

sealed class HomeUiIntent {
    data object GetDollarQuote : HomeUiIntent()
    data object GetUIT : HomeUiIntent()
    data object GetGratitude : HomeUiIntent()
    data object GetSections : HomeUiIntent()
}