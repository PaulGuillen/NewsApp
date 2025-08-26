package com.devpaul.emergency.ui.details

sealed class DetailsUiIntent {
    data object CallGeneralService : DetailsUiIntent()
    data class CallNumber(val phone: String) : DetailsUiIntent()
}