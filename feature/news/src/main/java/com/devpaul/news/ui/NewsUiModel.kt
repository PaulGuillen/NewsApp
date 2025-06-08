package com.devpaul.news.ui

import com.devpaul.news.domain.entity.CountryEntity

data class NewsUiModel(
    val country: CountryEntity? = null,
    val countryError: String? = null,
)