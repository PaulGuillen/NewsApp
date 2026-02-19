package com.devpaul.emergency.ui.details

import com.devpaul.emergency.ui.details.components.Region

sealed class DetailsUiIntent {
    data object CallGeneralService : DetailsUiIntent()
    data class ChangePoliceRegion(val region: Region) : DetailsUiIntent()
    data class CallNumber(val phone: String) : DetailsUiIntent()
}