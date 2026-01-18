package com.devpaul.news.ui.news

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectDataEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity
import com.devpaul.shared.ui.components.organisms.sourceselector.Source

data class NewsUiState(
    val country: ResultState<CountryEntity> = ResultState.Loading,
    val google: ResultState<GoogleEntity> = ResultState.Loading,
    val deltaProject: ResultState<DeltaProjectDataEntity> = ResultState.Loading,
    val reddit: ResultState<RedditEntity> = ResultState.Loading,
    val selectedCountry: CountryItemEntity? = null,
    val selectedSource: Source = Source.GOOGLE,
    val isNewsDetailLoading: Boolean = false,
    val coachMarkStepIndex: Int = 0,
    val showCoachMark: Boolean = true
)