package com.devpaul.news.ui.news_detail

import com.devpaul.news.domain.entity.CountryItemEntity

sealed class NewsDetailUiIntent {
    data class GetGoogleNews(val country: CountryItemEntity?, val page: Int) : NewsDetailUiIntent()
    data class GetDeltaProjectNews(val country: CountryItemEntity?, val page: Int) : NewsDetailUiIntent()
    data class GetRedditNews(val country: CountryItemEntity?, val page: Int) : NewsDetailUiIntent()
}