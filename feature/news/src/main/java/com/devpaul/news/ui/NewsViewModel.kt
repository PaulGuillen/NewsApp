package com.devpaul.news.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.core_platform.lifecycle.launch
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
            is NewsUiIntent.GetCountries -> fetchCountry()
            is NewsUiIntent.SelectCountry -> {
                updateUiStateOnMain { it.copy(selectedCountry = intent.country) }
                launchConcurrentRequests(
                    { fetchGoogleNews(intent.country) },
                    { fetchDeltaNews(intent.country) },
                    { fetchRedditNews(intent.country) }
                )
            }
        }
    }

    private suspend fun fetchCountry() {
        updateUiStateOnMain { it.copy(isCountryLoading = true) }
        val result = countryUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is CountryUC.Success.CountrySuccess -> {
                        NewsUiEvent.CountrySuccess(it.country).send()
                    }
                }
            }
            .onFailure<CountryUC.Failure> {
                when (it) {
                    is CountryUC.Failure.CountryError -> {
                        NewsUiEvent.CountryError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isCountryLoading = false) }
            }
    }

    private suspend fun fetchGoogleNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isGoogleLoading = true) }
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
                        NewsUiEvent.GoogleSuccess(it.google).send()
                    }
                }
            }
            .onFailure<GoogleUC.Failure> {
                when (it) {
                    is GoogleUC.Failure.GoogleError -> {
                        NewsUiEvent.GoogleError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isGoogleLoading = false) }
            }
    }

    private suspend fun fetchDeltaNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isDeltaProjectLoading = true) }
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
                        NewsUiEvent.DeltaProjectSuccess(it.deltaProject).send()
                    }
                }
            }
            .onFailure<DeltaProjectUC.Failure> {
                when (it) {
                    is DeltaProjectUC.Failure.DeltaProjectError -> {
                        NewsUiEvent.DeltaProjectError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isDeltaProjectLoading = false) }
            }
    }

    private suspend fun fetchRedditNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isRedditLoading = true) }
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
                        NewsUiEvent.RedditSuccess(it.reddit).send()
                    }
                }
            }
            .onFailure<RedditUC.Failure> {
                when (it) {
                    is RedditUC.Failure.RedditError -> {
                        NewsUiEvent.RedditError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isRedditLoading = false) }
            }
    }
}