package com.devpaul.infoxperu.feature.home.news.ui

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse

sealed class NewsUiEvent


sealed class NewsUiIntent {
    data object Country : NewsUiIntent()
}

data class NewsUiState(
    val countryState: ResultState<List<Country>>,
    val googleNewsState: ResultState<GoogleNewsJSON>,
    val projectGDELTState: ResultState<GDELProject>,
    val redditState: ResultState<RedditResponse>,
    val newsAPIState: ResultState<NewsResponse>,
    val selectedCountry: Country?
)

data class NewsContentCallbacks(
    val onCountrySelected: (Country) -> Unit
)
