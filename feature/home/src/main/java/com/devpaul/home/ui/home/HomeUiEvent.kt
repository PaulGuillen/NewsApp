package com.devpaul.home.ui.home

sealed class HomeUiEvent

sealed class HomeUiIntent {
    data object DollarQuote : HomeUiIntent()
    data object UIT : HomeUiIntent()
    data object Gratitude : HomeUiIntent()
    data object Sections : HomeUiIntent()
}