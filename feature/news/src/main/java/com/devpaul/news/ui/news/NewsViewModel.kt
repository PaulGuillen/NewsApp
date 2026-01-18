package com.devpaul.news.ui.news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.usecase.CountryUC
import com.devpaul.news.domain.usecase.DeltaProjectUC
import com.devpaul.news.domain.usecase.GoogleUC
import com.devpaul.news.domain.usecase.RedditUC
import com.devpaul.shared.ui.components.organisms.sourceselector.Source
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

    private val _selectedUrl = mutableStateOf<String?>(null)
    val selectedUrl: State<String?> get() = _selectedUrl

    init {
        NewsUiIntent.GetCountries.execute()
    }

    override suspend fun onUiIntent(intent: NewsUiIntent) {
        when (intent) {

            is NewsUiIntent.GetCountries -> fetchCountry()
            is NewsUiIntent.SelectCountry -> selectCountry(intent.country)
            is NewsUiIntent.SelectSource -> selectSource(intent.source)
            is NewsUiIntent.RetrySelectedSource -> retrySelectedSource()
        }
    }

    private suspend fun fetchCountry() {
        updateUiStateOnMain { it.copy(country = ResultState.Loading) }

        when (val result = countryUC.countryService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(country = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        country = ResultState.Error(message = result.error.message ?: ERROR_COUNTRY)
                    )
                }
            }
        }
    }

    private suspend fun selectCountry(country: CountryItemEntity) {
        updateUiStateOnMain {
            it.copy(selectedCountry = country)
        }

        when (uiState.selectedSource) {
            Source.GOOGLE -> fetchGoogleNews(country)
            Source.REDDIT -> fetchRedditNews(country)
            Source.DELTA -> fetchDeltaNews(country)
        }
    }

    private suspend fun selectSource(source: Source) {
        updateUiStateOnMain {
            it.copy(selectedSource = source)
        }

        val country = uiState.selectedCountry ?: return

        when (source) {
            Source.GOOGLE -> {
                if (uiState.google !is ResultState.Success) {
                    fetchGoogleNews(country)
                }
            }

            Source.REDDIT -> {
                if (uiState.reddit !is ResultState.Success) {
                    fetchRedditNews(country)
                }
            }

            Source.DELTA -> {
                if (uiState.deltaProject !is ResultState.Success) {
                    fetchDeltaNews(country)
                }
            }
        }
    }

    private suspend fun retrySelectedSource() {
        val country = uiState.selectedCountry ?: return

        when (uiState.selectedSource) {
            Source.GOOGLE -> fetchGoogleNews(country)
            Source.REDDIT -> fetchRedditNews(country)
            Source.DELTA -> fetchDeltaNews(country)
        }
    }

    private suspend fun fetchGoogleNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(google = ResultState.Loading) }
        val result = googleUC(
            GoogleUC.Params(
                q = country.category,
                hl = GOOGLE_LANG,
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
                                    message = it.error.apiErrorResponse?.message ?: ERROR_GOOGLE
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchDeltaNews(country: CountryItemEntity) {
        updateUiStateOnMain {
            it.copy(deltaProject = ResultState.Loading)
        }

        val result = deltaProjectUC(
            params = DeltaProjectUC.Params(
                q = country.category,
                mode = DELTA_MODE,
                format = DELTA_FORMAT,
            )
        )

        when (result) {

            is ResultState.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        deltaProject = ResultState.Success(result.response)
                    )
                }
            }

            is ResultState.Error -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        deltaProject = ResultState.Error(result.message)
                    )
                }
            }

            else -> {}
        }
    }

    private suspend fun fetchRedditNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(reddit = ResultState.Loading) }
        val result = redditUC(
            RedditUC.Params(
                country = country.category,
            )
        )
        when (result) {
            is ResultState.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(reddit = ResultState.Success(result.response))
                }
            }

            is ResultState.Error -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(reddit = ResultState.Error(result.message))
                }
            }

            else -> {}
        }
    }

    fun selectUrl(url: String) {
        _selectedUrl.value = url
    }

    companion object {
        const val PAGE_SIZE = 10
        const val GOOGLE_LANG = "es"
        const val DELTA_MODE = "ArtList"
        const val DELTA_FORMAT = "json"
        const val ERROR_COUNTRY = "Error al cargar las secciones"
        const val ERROR_GOOGLE = "Error al cargar las noticias de Google"
    }
}