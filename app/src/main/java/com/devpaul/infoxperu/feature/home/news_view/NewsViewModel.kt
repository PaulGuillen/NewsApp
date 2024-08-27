package com.devpaul.infoxperu.feature.home.news_view

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.BaseViewModel
import com.devpaul.infoxperu.domain.models.res.Country
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : BaseViewModel<NewsUiEvent>(dataStoreUseCase)  {

    private val _countryState = MutableStateFlow<ResultState<List<Country>>>(ResultState.Loading)
    val countryState: StateFlow<ResultState<List<Country>>> = _countryState

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
                }
                _countryState.value = ResultState.Success(countryList)
            }
            .addOnFailureListener { exception ->
                _countryState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

}