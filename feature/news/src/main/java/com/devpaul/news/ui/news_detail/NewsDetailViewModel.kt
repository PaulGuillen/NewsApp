package com.devpaul.news.ui.news_detail

import android.net.Uri
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.news.domain.entity.CountryItemEntity
import com.devpaul.news.domain.usecase.DeltaProjectUC
import com.devpaul.news.domain.usecase.GoogleUC
import com.devpaul.news.domain.usecase.RedditUC
import com.google.gson.Gson
import org.koin.android.annotation.KoinViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

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
    private var selectedCountry: CountryItemEntity? = null
    private var currentPage = 1
    private var totalPages = 1
    private var currentNewsType: String? = null
    private val _selectedUrl = mutableStateOf<String?>(null)
    val selectedUrl: State<String?> get() = _selectedUrl
    
    fun setNewsData(
        newsType: String?,
        country: String?
    ) {
        val gson = Gson()
        val decodedCountryJson = Uri.decode(country)
        selectedCountry = gson.fromJson(decodedCountryJson, CountryItemEntity::class.java)
        currentNewsType = newsType
        currentPage = 1
        when (newsType) {
            "googleNews" -> NewsDetailUiIntent.GetGoogleNews(
                country = selectedCountry,
                page = currentPage
            ).execute()

            "deltaProjectNews" -> NewsDetailUiIntent.GetDeltaProjectNews(
                country = selectedCountry,
                page = currentPage
            ).execute()

            "redditNews" -> NewsDetailUiIntent.GetRedditNews(
                country = selectedCountry,
                page = currentPage
            ).execute()
        }
    }

    override suspend fun onUiIntent(intent: NewsDetailUiIntent) {
        when (intent) {
            is NewsDetailUiIntent.GetGoogleNews -> {
                launchIO { intent.country?.let { fetchGoogleNews(it, intent.page) } }
            }

            is NewsDetailUiIntent.GetDeltaProjectNews -> {
                launchIO { intent.country?.let { fetchDeltaNews(it, intent.page) } }
            }

            is NewsDetailUiIntent.GetRedditNews -> {
                launchIO { intent.country?.let { fetchRedditNews(it, intent.page) } }
            }

            is NewsDetailUiIntent.LoadNextPage -> {
                launchIO { loadNextPage(intent.newsType) }
            }
        }
    }

    private suspend fun loadNextPage(newsType: String) {
        if (!canLoadMore(newsType)) return

        updateUiStateOnMain { it.copy(isLoadingMore = true) }

        selectedCountry?.let {
            currentPage++
            when (newsType) {
                "googleNews" -> NewsDetailUiIntent.GetGoogleNews(country = it, page = currentPage)
                    .execute()

                "deltaProjectNews" -> NewsDetailUiIntent.GetDeltaProjectNews(
                    country = it,
                    page = currentPage
                ).execute()

                "redditNews" -> NewsDetailUiIntent.GetRedditNews(country = it, page = currentPage)
                    .execute()
            }
        }
    }

    fun canLoadMore(newsType: String): Boolean {
        return when (newsType) {
            "googleNews" -> {
                val current =
                    (uiState.google as? ResultState.Success)?.response?.data?.currentPage ?: 1
                val total =
                    (uiState.google as? ResultState.Success)?.response?.data?.totalPages ?: 1
                current < total
            }

            "deltaProjectNews" -> {
                val current =
                    (uiState.deltaProject as? ResultState.Success)?.response?.data?.currentPage ?: 1
                val total =
                    (uiState.deltaProject as? ResultState.Success)?.response?.data?.totalPages ?: 1
                current < total
            }

            "redditNews" -> {
                val current =
                    (uiState.reddit as? ResultState.Success)?.response?.data?.currentPage ?: 1
                val total =
                    (uiState.reddit as? ResultState.Success)?.response?.data?.totalPages ?: 1
                current < total
            }

            else -> false
        }
    }


    private suspend fun fetchGoogleNews(country: CountryItemEntity, page: Int) {
        val result = googleUC(
            GoogleUC.Params(
                q = country.category,
                hl = "es",
                page = page,
                perPage = 10
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GoogleUC.Success.GoogleSuccess -> {
                        totalPages = it.google.data.totalPages
                        updateUiStateOnMain { uiState ->
                            val previous =
                                (uiState.google as? ResultState.Success)?.response?.data?.items
                                    ?: emptyList()
                            uiState.copy(
                                google = ResultState.Success(
                                    it.google.copy(
                                        data = it.google.data.copy(
                                            items = previous + it.google.data.items
                                        )
                                    )
                                )
                            )
                        }
                    }
                }
            }
            .onFailure<GoogleUC.Failure> {
                updateUiStateOnMain {
                    it.copy(google = ResultState.Error(message = "Error al cargar las noticias de Google"))
                }
            } .also {
                updateUiStateOnMain { it.copy(isLoadingMore = false) }
            }
    }

    private suspend fun fetchDeltaNews(country: CountryItemEntity, page: Int) {
        val result = deltaProjectUC(
            DeltaProjectUC.Params(
                q = country.category,
                mode = "ArtList",
                format = "json",
                page = page,
                perPage = 5
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is DeltaProjectUC.Success.DeltaProjectSuccess -> {
                        totalPages = it.deltaProject.data.totalPages
                        updateUiStateOnMain { uiState ->
                            val previous =
                                (uiState.deltaProject as? ResultState.Success)?.response?.data?.items
                                    ?: emptyList()
                            uiState.copy(
                                deltaProject = ResultState.Success(
                                    it.deltaProject.copy(
                                        data = it.deltaProject.data.copy(items = previous + it.deltaProject.data.items)
                                    )
                                )
                            )
                        }
                    }
                }
            }
            .onFailure<DeltaProjectUC.Failure> {
                updateUiStateOnMain {
                    it.copy(deltaProject = ResultState.Error(message = "Error al cargar las noticias de Delta Project"))
                }
            } . also {
                updateUiStateOnMain { it.copy(isLoadingMore = false) }
            }
    }

    private suspend fun fetchRedditNews(country: CountryItemEntity, page: Int) {
        val result = redditUC(
            RedditUC.Params(
                country = country.category,
                page = page,
                perPage = 10
            )
        )
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is RedditUC.Success.RedditSuccess -> {
                        totalPages = it.reddit.data.totalPages
                        updateUiStateOnMain { uiState ->
                            val previous =
                                (uiState.reddit as? ResultState.Success)?.response?.data?.items
                                    ?: emptyList()
                            uiState.copy(
                                reddit = ResultState.Success(
                                    it.reddit.copy(
                                        data = it.reddit.data.copy(
                                            items = previous + it.reddit.data.items
                                        )
                                    )
                                )
                            )
                        }
                    }
                }
            }
            .onFailure<RedditUC.Failure> {
                updateUiStateOnMain {
                    it.copy(reddit = ResultState.Error(message = "Error al cargar las noticias de Reddit"))
                }
            } .onFailure {
                updateUiStateOnMain { it.copy(isLoadingMore = false) }
            }
    }

    fun selectUrl(url: String) {
        _selectedUrl.value = url
    }
}
