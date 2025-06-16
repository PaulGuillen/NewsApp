package com.devpaul.home.ui

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity

data class HomeUiState(
    val dollarQuote: ResultState<DollarQuoteEntity> = ResultState.Loading,
    val uitValue: ResultState<UITEntity> = ResultState.Loading,
    val section: ResultState<SectionEntity> = ResultState.Loading,
    val gratitude: ResultState<GratitudeEntity> = ResultState.Loading
)