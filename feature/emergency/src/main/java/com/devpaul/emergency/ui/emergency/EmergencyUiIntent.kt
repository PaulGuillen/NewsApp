package com.devpaul.emergency.ui.emergency

sealed class EmergencyUiIntent {
    data object GetEmergencyServices : EmergencyUiIntent()
    data class NavigateToDetails(val type: String) : EmergencyUiIntent()
}