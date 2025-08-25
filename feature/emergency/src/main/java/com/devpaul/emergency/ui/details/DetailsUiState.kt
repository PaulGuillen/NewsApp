package com.devpaul.emergency.ui.details

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.GeneralEntity

data class DetailsUiState(
    val generalCase: Boolean = false,
    val general: ResultState<GeneralEntity> = ResultState.Loading,
    val civilCase : Boolean = false,
    val civilDefense: ResultState<GeneralEntity> = ResultState.Loading,
    val securityCase : Boolean = false,
    val limaSecurity: ResultState<GeneralEntity> = ResultState.Loading,
    val provinceSecurity: ResultState<GeneralEntity> = ResultState.Loading,
)