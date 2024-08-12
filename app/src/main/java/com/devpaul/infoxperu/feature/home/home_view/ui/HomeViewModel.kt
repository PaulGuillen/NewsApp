package com.devpaul.infoxperu.feature.home.home_view.ui

import androidx.lifecycle.*
import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.domain.models.res.ApiException
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.feature.home.home_view.uc.HomeUseCase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCase: HomeUseCase,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _dollarQuoteState = MutableStateFlow<ResultState<DollarQuoteResponse>?>(null)
    val dollarQuoteState: StateFlow<ResultState<DollarQuoteResponse>?> = _dollarQuoteState

    private val _gratitude = MutableStateFlow<List<Gratitude>>(emptyList())
    val gratitude: StateFlow<List<Gratitude>> = _gratitude

    init {
        getDollarQuote()
        fetchGratitude()
    }

    private fun getDollarQuote() {
        _dollarQuoteState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val result = homeUseCase()
                _dollarQuoteState.value = result
            } catch (e: ApiException) {
                _dollarQuoteState.value = ResultState.Error(e)
            } catch (e: Exception) {
                _dollarQuoteState.value = ResultState.Error(e)
            }
        }
    }

    private fun fetchGratitude() {
        firestore.collection("gratitude")
            .get()
            .addOnSuccessListener { documents ->
                val gratitudeList = documents.map { document ->
                    document.toObject(Gratitude::class.java)
                }
                _gratitude.value = gratitudeList
            }
            .addOnFailureListener {
             Timber.e(it)
            }
    }

}
