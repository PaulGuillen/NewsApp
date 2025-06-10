package com.devpaul.news.ui.news

import com.devpaul.news.domain.entity.CountryItemEntity

data class NewsUiState(
    val isCountryLoading: Boolean = false,
    val isGoogleLoading: Boolean = false,
    val isDeltaProjectLoading: Boolean = false,
    val isRedditLoading: Boolean = false,
    val selectedCountry: CountryItemEntity? = null,
)