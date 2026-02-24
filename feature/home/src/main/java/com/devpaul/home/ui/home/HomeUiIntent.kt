package com.devpaul.home.ui.home

sealed class HomeUiIntent {
    data object GetHomeServices : HomeUiIntent()
    data object GetDollarQuote : HomeUiIntent()
    data object GetSections : HomeUiIntent()
    data object GetUITValue : HomeUiIntent()
    data object ShowEmergencySheet : HomeUiIntent()
    data object HideEmergencySheet : HomeUiIntent()
    data class DialEmergency(val number: String) : HomeUiIntent()
}