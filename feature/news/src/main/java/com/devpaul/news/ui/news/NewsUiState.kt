package com.devpaul.news.ui.news

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

data class NewsUiState(
    val country: ResultState<CountryEntity> = ResultState.Loading,
    val google: ResultState<GoogleEntity> = ResultState.Loading,
    val deltaProject: ResultState<DeltaProjectEntity> = ResultState.Loading,
    val reddit: ResultState<RedditEntity> = ResultState.Loading,
    val selectedCountry: CountryItemEntity? = null,
)