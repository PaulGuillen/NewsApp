package com.devpaul.news.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.usecase.CountryUC
import com.devpaul.news.domain.usecase.GoogleUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NewsViewModel(
    private val countryUC: CountryUC,
    private val googleUC: GoogleUC,
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
                fetchNews(intent.country)
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

    private suspend fun fetchNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(selectedCountry = country) }
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

}