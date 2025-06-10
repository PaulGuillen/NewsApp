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
import timber.log.Timber

@KoinViewModel
class NewsDetailViewModel(
    private val countryUC: CountryUC,
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
        Timber.d("NewsType set to: $newsType, Country set to: $selectedCountry")
    }

    override suspend fun onUiIntent(intent: NewsDetailUiIntent) {
        TODO("Not yet implemented")
    }

}