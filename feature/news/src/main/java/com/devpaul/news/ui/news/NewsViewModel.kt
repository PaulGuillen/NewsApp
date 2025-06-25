package com.devpaul.news.ui.news

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.usecase.CountryUC
import com.devpaul.news.domain.usecase.DeltaProjectUC
import com.devpaul.news.domain.usecase.GoogleUC
import com.devpaul.news.domain.usecase.RedditUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NewsViewModel(
    private val countryUC: CountryUC,
    private val googleUC: GoogleUC,
    private val deltaProjectUC: DeltaProjectUC,
    private val redditUC: RedditUC,
) : StatefulViewModel<NewsUiState, NewsUiIntent, NewsUiEvent>(
    defaultUIState = {
        NewsUiState()
    }
) {

    init {
        NewsUiIntent.GetCountries.execute()
    }

    override suspend fun onUiIntent(intent: NewsUiIntent) {
        when (intent) {
            is NewsUiIntent.GetCountries -> launchIO { fetchCountry() }
            is NewsUiIntent.SelectCountry -> {
                updateUiStateOnMain { it.copy(selectedCountry = intent.country) }
                launchConcurrentRequests(
                    { fetchGoogleNews(intent.country) },
                    { fetchDeltaNews(intent.country) },
                    { fetchRedditNews(intent.country) }
                )
            }

            is NewsUiIntent.GetDeltaProject -> {
                launchIO {
                    fetchDeltaNews(intent.country)
                }
            }

            is NewsUiIntent.GetReddit -> {
                launchIO {
                    fetchRedditNews(intent.country)
                }
            }

            is NewsUiIntent.GetGoogleNews -> {
                launchIO {
                    fetchGoogleNews(intent.country)
                }
            }
        }
    }

    private suspend fun fetchCountry() {
        updateUiStateOnMain { it.copy(country = ResultState.Loading) }
        val result = countryUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is CountryUC.Success.CountrySuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(country = ResultState.Success(it.country))
                        }
                    }
                }
            }
            .onFailure<CountryUC.Failure> {
                when (it) {
                    is CountryUC.Failure.CountryError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                country = ResultState.Error(
                                    it.error.apiErrorResponse?.message
                                        ?: "Error al cargar los países"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchGoogleNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(google = ResultState.Loading) }
        val result = googleUC(
            GoogleUC.Params(
                q = country.category,
                hl = "es",
                page = 1,
                perPage = 10
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GoogleUC.Success.GoogleSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(google = ResultState.Success(it.google))
                        }
                    }
                }
            }
            .onFailure<GoogleUC.Failure> {
                when (it) {
                    is GoogleUC.Failure.GoogleError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                google = ResultState.Error(
                                    it.error.apiErrorResponse?.message
                                        ?: "Error al cargar las noticias de Google"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchDeltaNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(deltaProject = ResultState.Loading) }
        val result = deltaProjectUC(
            DeltaProjectUC.Params(
                q = country.category,
                mode = "ArtList",
                format = "json",
                page = 1,
                perPage = 5
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is DeltaProjectUC.Success.DeltaProjectSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(deltaProject = ResultState.Success(it.deltaProject))
                        }
                    }
                }
            }
            .onFailure<DeltaProjectUC.Failure> {
                when (it) {
                    is DeltaProjectUC.Failure.DeltaProjectError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                deltaProject = ResultState.Error(
                                    it.error.apiErrorResponse?.message
                                        ?: "Error al cargar las noticias de Delta Project"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchRedditNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(reddit = ResultState.Loading) }
        val result = redditUC(
            RedditUC.Params(
                country = country.category,
                page = 1,
                perPage = 10
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is RedditUC.Success.RedditSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(reddit = ResultState.Success(it.reddit))
                        }
                    }
                }
            }
            .onFailure<RedditUC.Failure> {
                when (it) {
                    is RedditUC.Failure.RedditError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                reddit = ResultState.Error(
                                    it.error.apiErrorResponse?.message
                                        ?: "Error al cargar las noticias de Reddit"
                                )
                            )
                        }
                    }
                }
            }
    }
}