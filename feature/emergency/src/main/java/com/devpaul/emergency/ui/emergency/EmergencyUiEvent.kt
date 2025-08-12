package com.devpaul.emergency.ui.emergency

sealed class EmergencyUiEvent {
    data class NavigateToDetails(val type: String) : EmergencyUiEvent()
}