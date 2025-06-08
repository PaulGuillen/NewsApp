package com.devpaul.news.ui

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.news.domain.entity.CountryEntity

sealed class NewsUiEvent {
    data class CountrySuccess(val response: CountryEntity) : NewsUiEvent()
    data class CountryError(val error: Defaults.HttpError<String>) : NewsUiEvent()
}