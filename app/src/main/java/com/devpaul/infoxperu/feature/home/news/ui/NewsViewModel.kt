package com.devpaul.infoxperu.feature.home.news.ui

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.GDELTUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.GoogleNewsUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.NewsAPIUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.RedditUseCase
import com.devpaul.infoxperu.feature.util.Constant
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val googleNewsUseCase: GoogleNewsUseCase,
    private val projectGDELTUseCase: GDELTUseCase,
    private val redditUseCase: RedditUseCase,
    private val newsAPIUseCase: NewsAPIUseCase,
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : StatelessViewModel<NewsUiEvent, NewsUiIntent>(dataStoreUseCase) {

    private val _countryState = MutableStateFlow<ResultState<List<Country>>>(ResultState.Loading)
    val countryState: StateFlow<ResultState<List<Country>>> = _countryState

    private val _googleNewsState =
        MutableStateFlow<ResultState<GoogleNewsJSON>>(ResultState.Loading)
    val googleNewsState: StateFlow<ResultState<GoogleNewsJSON>> = _googleNewsState

    private val _projectGDELTState =
        MutableStateFlow<ResultState<GDELProject>>(ResultState.Loading)
    val projectGDELTState: StateFlow<ResultState<GDELProject>> = _projectGDELTState

    private val _redditState =
        MutableStateFlow<ResultState<RedditResponse>>(ResultState.Loading)
    val redditState: StateFlow<ResultState<RedditResponse>> = _redditState

    private val _newsAPIState =
        MutableStateFlow<ResultState<NewsResponse>>(ResultState.Loading)
    val newsAPIState: StateFlow<ResultState<NewsResponse>> = _newsAPIState

    override fun handleIntent(intent: NewsUiIntent) {
        when (intent) {
            is NewsUiIntent.Country -> fetchCountry()
        }
    }

    private fun fetchCountry() {
        _countryState.value = ResultState.Loading

        executeInScope(
            block = {
                firestore.collection(Constant.COUNTRY_COLLECTION)
                    .get()
                    .addOnSuccessListener { documents ->
                        val countryList = documents.map { document ->
                            document.toObject(Country::class.java)
                        }.sortedWith(compareBy { country ->
                            when (country.title) {
                                Constant.COUNTRY_PERU -> 1
                                Constant.COUNTRY_ARGENTINA -> 2
                                Constant.COUNTRY_MEXICO -> 3
                                else -> 4
                            }
                        })
                        _countryState.value = ResultState.Success(countryList)
                    }
                    .addOnFailureListener { exception ->
                        _countryState.value =
                            ResultState.Error(
                                exception = exception as? Exception ?: Exception(
                                    exception
                                )
                            )
                    }
            },
            onError = { error ->
                _countryState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    fun getGoogleNews(query: String, language: String, limit: Int) {
        _googleNewsState.value = ResultState.Loading

        executeInScope(
            block = {
                val result = googleNewsUseCase(limit, query, language)
                _googleNewsState.value = result
            },
            onError = { error ->
                _googleNewsState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    fun getProjectGDELTNews(query: String, mode: String, format: String, limit: Int) {
        _projectGDELTState.value = ResultState.Loading

        executeInScope(
            block = {
                val result = projectGDELTUseCase(limit, query, mode, format)
                _projectGDELTState.value = result
            },
            onError = { error ->
                _projectGDELTState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    fun getRedditNews(limit: Int, country: String) {
        _redditState.value = ResultState.Loading

        executeInScope(
            block = {
                val result = redditUseCase(limit, country)
                _redditState.value = result
            },
            onError = { error ->
                _redditState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    fun getNewsAPI(limit: Int, initLetters: String) {
        _newsAPIState.value = ResultState.Loading

        executeInScope(
            block = {
                val result = newsAPIUseCase(limit, initLetters)
                _newsAPIState.value = result
            },
            onError = { error ->
                _newsAPIState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }
}