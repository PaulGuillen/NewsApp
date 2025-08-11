package com.devpaul.emergency.ui.emergency

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.emergency.domain.entity.SectionEntity

data class EmergencyUiState(
    val section : ResultState<SectionEntity> = ResultState.Loading,
)