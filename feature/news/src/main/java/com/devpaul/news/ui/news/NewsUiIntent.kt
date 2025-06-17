package com.devpaul.news.ui.news

import com.devpaul.news.domain.entity.CountryItemEntity

sealed class NewsUiIntent {
    data object GetCountries : NewsUiIntent()
    data class SelectCountry(val country: CountryItemEntity) : NewsUiIntent()
    data class GetDeltaProject(val country: CountryItemEntity): NewsUiIntent()
    data class GetReddit(val country: CountryItemEntity): NewsUiIntent()
    data class GetGoogleNews(val country: CountryItemEntity): NewsUiIntent()
}