package com.devpaul.home.ui.home

sealed class HomeUiEvent {
    data class DialNumber(val number: String) : HomeUiEvent()
}