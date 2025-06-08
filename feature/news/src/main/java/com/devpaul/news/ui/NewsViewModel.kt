package com.devpaul.news.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.usecase.CountryUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NewsViewModel(
    private val countryUC: CountryUC,
) : StatefulViewModel<NewsUiState, NewsUiIntent, NewsUiEvent>(
    defaultUIState = {
        NewsUiState()
    }
) {

    init {
       NewsUiIntent.GetCountries.execute()
    }

    override suspend fun onUiIntent(intent: NewsUiIntent) {
      when(intent){
          NewsUiIntent.GetCountries -> fetchCountry()
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

}