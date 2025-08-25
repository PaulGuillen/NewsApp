package com.devpaul.emergency.ui.details

sealed class DetailsUiEvent {
    data class RequireCallPermission(val phone: String) : DetailsUiEvent()
}