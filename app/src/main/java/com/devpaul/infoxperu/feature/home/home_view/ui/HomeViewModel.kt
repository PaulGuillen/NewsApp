package com.devpaul.infoxperu.feature.home.home_view.ui

import androidx.lifecycle.*
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.feature.home.home_view.uc.DollarQuoteUseCase
import com.devpaul.infoxperu.feature.home.home_view.uc.UITUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dollarQuoteUseCase: DollarQuoteUseCase,
    private val uitUseCase: UITUseCase,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _dollarQuoteState = MutableStateFlow<ResultState<DollarQuoteResponse>?>(null)
    val dollarQuoteState: StateFlow<ResultState<DollarQuoteResponse>?> = _dollarQuoteState

    private val _uitState = MutableStateFlow<ResultState<UITResponse>?>(null)
    val uitState: StateFlow<ResultState<UITResponse>?> = _uitState

    private val _gratitudeState = MutableStateFlow<ResultState<List<Gratitude>>>(ResultState.Loading)
    val gratitudeState: StateFlow<ResultState<List<Gratitude>>> = _gratitudeState

    private val _sectionsState = MutableStateFlow<ResultState<List<SectionItem>>>(ResultState.Loading)
    val sectionsState: StateFlow<ResultState<List<SectionItem>>> = _sectionsState

    init {
        getDollarQuote()
        getUit()
        fetchGratitude()
        fetchSections()
    }

    private fun getDollarQuote() {
        _dollarQuoteState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = dollarQuoteUseCase()
                _dollarQuoteState.value = result
            } catch (e: ApiException) {
                _dollarQuoteState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _dollarQuoteState.value = ResultState.Error(e)
            }
        }
    }

    private fun getUit() {
        _uitState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = uitUseCase()
                _uitState.value = result
            } catch (e: ApiException) {
                _uitState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _uitState.value = ResultState.Error(e)
            }
        }
    }

    private fun fetchGratitude() {
        _gratitudeState.value = ResultState.Loading

        firestore.collection("gratitude")
            .get()
            .addOnSuccessListener { documents ->
                val gratitudeList = documents.map { document ->
                    document.toObject(Gratitude::class.java)
                }
                _gratitudeState.value = ResultState.Success(gratitudeList)
            }
            .addOnFailureListener { exception ->
                _gratitudeState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }


    private fun fetchSections() {
        _sectionsState.value = ResultState.Loading

        firestore.collection("sectionItems")
            .get()
            .addOnSuccessListener { result ->
                val sectionList = result.map { document ->
                    document.toObject(SectionItem::class.java)
                }
                _sectionsState.value = ResultState.Success(sectionList)
            }
            .addOnFailureListener { exception ->
                _sectionsState.value = ResultState.Error(exception)
                Timber.e(exception)
            }
    }

}
