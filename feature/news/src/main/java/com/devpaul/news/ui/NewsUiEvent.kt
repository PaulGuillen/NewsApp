package com.devpaul.news.ui

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.news.domain.entity.CountryEntity
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity

sealed class NewsUiEvent {
    data class CountrySuccess(val response: CountryEntity) : NewsUiEvent()
    data class CountryError(val error: Defaults.HttpError<String>) : NewsUiEvent()

    data class GoogleSuccess(val response: GoogleEntity) : NewsUiEvent()
    data class GoogleError(val error: Defaults.HttpError<String>) : NewsUiEvent()

    data class DeltaProjectSuccess(val response: DeltaProjectEntity) : NewsUiEvent()
    data class DeltaProjectError(val error: Defaults.HttpError<String>) : NewsUiEvent()
}