package com.devpaul.news.ui.news_detail

import com.devpaul.news.domain.entity.CountryItemEntity

data class NewsDetailUiState(
    val country: CountryItemEntity? = null,
    val newsType: String = "",
)