package com.devpaul.home.ui.acknowledgments

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.entity.GratitudeEntity

data class AcknowledgmentUiState(
    val gratitude: ResultState<GratitudeEntity> = ResultState.Loading
)