package com.devpaul.infoxperu.feature.home.news_view

import androidx.lifecycle.viewModelScope
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.models.res.GDELProject
import com.devpaul.infoxperu.domain.models.res.GoogleNewsJSON
import com.devpaul.infoxperu.domain.models.res.NewsResponse
import com.devpaul.infoxperu.domain.models.res.RedditResponse
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.home_view.uc.GDELTUseCase
import com.devpaul.infoxperu.feature.home.home_view.uc.GoogleNewsUseCase
import com.devpaul.infoxperu.feature.home.home_view.uc.NewsAPIUseCase
import com.devpaul.infoxperu.feature.home.home_view.uc.RedditUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val googleNewsUseCase: GoogleNewsUseCase,
    private val projectGDELTUseCase: GDELTUseCase,
    private val redditUseCase: RedditUseCase,
    private val newsAPIUseCase: NewsAPIUseCase,
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<NewsUiEvent>(dataStoreUseCase) {

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

    init {
        fetchCountry()
    }

    private fun fetchCountry() {
        _countryState.value = ResultState.Loading

        firestore.collection("country")
            .get()
            .addOnSuccessListener { documents ->
                val countryList = documents.map { document ->
                    document.toObject(Country::class.java)
                }.sortedBy { it.title != "Perú" }
                _countryState.value = ResultState.Success(countryList)
            }
            .addOnFailureListener { exception ->
                _countryState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

    fun getGoogleNews(query: String, language: String) {
        _googleNewsState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = googleNewsUseCase(query, language)
                _googleNewsState.value = result
            } catch (e: ApiException) {
                _googleNewsState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _googleNewsState.value = ResultState.Error(e)
            }
        }
    }

    fun getProjectGDELTNews(query: String, mode: String, format: String) {
        _projectGDELTState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = projectGDELTUseCase(query, mode, format)
                _projectGDELTState.value = result
            } catch (e: ApiException) {
                _projectGDELTState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _projectGDELTState.value = ResultState.Error(e)
            }
        }
    }

    fun getRedditNews(country: String) {
        _redditState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = redditUseCase(country)
                _redditState.value = result
            } catch (e: ApiException) {
                _redditState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _redditState.value = ResultState.Error(e)
            }
        }
    }

    fun getNewsAPI(initLetters: String) {
        _newsAPIState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = newsAPIUseCase(initLetters)
                _newsAPIState.value = result
            } catch (e: ApiException) {
                _newsAPIState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _newsAPIState.value = ResultState.Error(e)
            }
        }
    }

}