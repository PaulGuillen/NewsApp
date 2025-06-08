package com.devpaul.news.ui

sealed class NewsUiIntent {
    data object GetCountries : NewsUiIntent()
}