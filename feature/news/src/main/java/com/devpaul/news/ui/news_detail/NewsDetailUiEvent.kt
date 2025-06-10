package com.devpaul.news.ui.news_detail

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.news.domain.entity.DeltaProjectEntity
import com.devpaul.news.domain.entity.GoogleEntity
import com.devpaul.news.domain.entity.RedditEntity

sealed class NewsDetailUiEvent {
    data class GoogleSuccess(val response: GoogleEntity) : NewsDetailUiEvent()
    data class GoogleError(val error: Defaults.HttpError<String>) : NewsDetailUiEvent()

    data class DeltaProjectSuccess(val response: DeltaProjectEntity) : NewsDetailUiEvent()
    data class DeltaProjectError(val error: Defaults.HttpError<String>) : NewsDetailUiEvent()

    data class RedditSuccess(val response: RedditEntity) : NewsDetailUiEvent()
    data class RedditError(val error: Defaults.HttpError<String>) : NewsDetailUiEvent()
}