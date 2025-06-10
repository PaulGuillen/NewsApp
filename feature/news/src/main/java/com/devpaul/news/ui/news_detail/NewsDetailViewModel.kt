package com.devpaul.news.ui.news_detail

import android.net.Uri
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.usecase.CountryUC
import com.devpaul.news.domain.usecase.DeltaProjectUC
import com.devpaul.news.domain.usecase.GoogleUC
import com.devpaul.news.domain.usecase.RedditUC
import com.google.gson.Gson
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class NewsDetailViewModel(
    private val googleUC: GoogleUC,
    private val deltaProjectUC: DeltaProjectUC,
    private val redditUC: RedditUC,
) : StatefulViewModel<
        NewsDetailUiState, NewsDetailUiIntent, NewsDetailUiEvent>(
    defaultUIState = {
        NewsDetailUiState()
    }
) {

    fun setNewsData(
        newsType: String?,
        country: String?
    ) {
        val gson = Gson()
        val decodedCountryJson = Uri.decode(country)
        val selectedCountry = gson.fromJson(decodedCountryJson, CountryItemEntity::class.java)
        when (newsType) {
            "googleNews" -> NewsDetailUiIntent.GetGoogleNews(country = selectedCountry).execute()
            "deltaProjectNews" -> NewsDetailUiIntent.GetDeltaProjectNews(country = selectedCountry)
                .execute()

            "redditNews" -> NewsDetailUiIntent.GetRedditNews(country = selectedCountry).execute()
            else -> {
                //
            }
        }
    }

    override suspend fun onUiIntent(intent: NewsDetailUiIntent) {
        when (intent) {
            is NewsDetailUiIntent.GetGoogleNews -> {
                launchIO { fetchGoogleNews(intent.country) }
            }

            is NewsDetailUiIntent.GetDeltaProjectNews -> {
                launchIO { fetchDeltaNews(intent.country) }
            }

            is NewsDetailUiIntent.GetRedditNews -> {
                launchIO { fetchRedditNews(intent.country) }
            }
        }
    }

    private suspend fun fetchGoogleNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isNewsDetailLoading = true) }
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
                        NewsDetailUiEvent.GoogleSuccess(it.google).send()
                    }
                }
            }
            .onFailure<GoogleUC.Failure> {
                when (it) {
                    is GoogleUC.Failure.GoogleError -> {
                        NewsDetailUiEvent.GoogleError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isNewsDetailLoading = false) }
            }
    }

    private suspend fun fetchDeltaNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isNewsDetailLoading = true) }
        val result = deltaProjectUC(
            DeltaProjectUC.Params(
                q = country.category,
                mode = "ArtList",
                format = "json",
                page = 1,
                perPage = 10
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is DeltaProjectUC.Success.DeltaProjectSuccess -> {
                        NewsDetailUiEvent.DeltaProjectSuccess(it.deltaProject).send()
                    }
                }
            }
            .onFailure<DeltaProjectUC.Failure> {
                when (it) {
                    is DeltaProjectUC.Failure.DeltaProjectError -> {
                        NewsDetailUiEvent.DeltaProjectError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isNewsDetailLoading = false) }
            }
    }

    private suspend fun fetchRedditNews(country: CountryItemEntity) {
        updateUiStateOnMain { it.copy(isNewsDetailLoading = true) }
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
                        NewsDetailUiEvent.RedditSuccess(it.reddit).send()
                    }
                }
            }
            .onFailure<RedditUC.Failure> {
                when (it) {
                    is RedditUC.Failure.RedditError -> {
                        NewsDetailUiEvent.RedditError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isNewsDetailLoading = false) }
            }
    }
}