package com.devpaul.emergency.ui.details

sealed class DetailsUiIntent {
    data class CallNumber(val phone: String) : DetailsUiIntent()
}