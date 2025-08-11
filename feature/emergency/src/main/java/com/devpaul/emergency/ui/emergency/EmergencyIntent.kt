package com.devpaul.emergency.ui.emergency

sealed class EmergencyIntent {
    data object GetEmergencyServices : EmergencyIntent()
}