package com.devpaul.infoxperu.feature.home.home.ui

import com.devpaul.infoxperu.core.extension.ResultState
import com.devpaul.infoxperu.core.viewmodel.StatelessViewModel
import com.devpaul.infoxperu.domain.models.res.DollarQuoteResponse
import com.devpaul.infoxperu.domain.models.res.Gratitude
import com.devpaul.infoxperu.domain.models.res.SectionItem
import com.devpaul.infoxperu.domain.models.res.UITResponse
import com.devpaul.infoxperu.domain.use_case.DataStoreUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.DollarQuoteUseCase
import com.devpaul.infoxperu.feature.home.home.domain.uc.UITUseCase
import com.devpaul.infoxperu.feature.util.Constant
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dollarQuoteUseCase: DollarQuoteUseCase,
    private val uitUseCase: UITUseCase,
    private val firestore: FirebaseFirestore,
    dataStoreUseCase: DataStoreUseCase
) : StatelessViewModel<HomeUiEvent, HomeUiIntent>(dataStoreUseCase) {

    private val _dollarQuoteState = MutableStateFlow<ResultState<DollarQuoteResponse>?>(null)
    val dollarQuoteState: StateFlow<ResultState<DollarQuoteResponse>?> = _dollarQuoteState

    private val _uitState = MutableStateFlow<ResultState<UITResponse>?>(null)
    val uitState: StateFlow<ResultState<UITResponse>?> = _uitState

    private val _gratitudeState =
        MutableStateFlow<ResultState<List<Gratitude>>>(ResultState.Loading)
    val gratitudeState: StateFlow<ResultState<List<Gratitude>>> = _gratitudeState

    private val _sectionsState =
        MutableStateFlow<ResultState<List<SectionItem>>>(ResultState.Loading)
    val sectionsState: StateFlow<ResultState<List<SectionItem>>> = _sectionsState

    override fun handleIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.DollarQuote -> fetchDollarQuote()
            is HomeUiIntent.UIT -> fetchUit()
            is HomeUiIntent.Gratitude -> fetchGratitude()
            is HomeUiIntent.Sections -> fetchSections()
        }
    }

    private fun fetchDollarQuote() {
        _dollarQuoteState.value = ResultState.Loading
        executeInScope(
            block = {
                val result = dollarQuoteUseCase()
                _dollarQuoteState.value = result
            },
            onError = { error ->
                _dollarQuoteState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    private fun fetchUit() {
        _uitState.value = ResultState.Loading
        executeInScope(
            block = {
                val result = uitUseCase()
                _uitState.value = result
            },
            onError = { error ->
                _uitState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    private fun fetchGratitude() {
        _gratitudeState.value = ResultState.Loading

        executeInScope(
            block = {
                firestore.collection(Constant.GRATITUDE)
                    .get()
                    .addOnSuccessListener { documents ->
                        val gratitudeList = documents.map { document ->
                            document.toObject(Gratitude::class.java)
                        }
                        _gratitudeState.value = ResultState.Success(data = gratitudeList)
                    }
                    .addOnFailureListener { exception ->
                        _gratitudeState.value = ResultState.Error(exception = exception)
                    }
            },
            onError = { error ->
                _gratitudeState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }

    private fun fetchSections() {
        _sectionsState.value = ResultState.Loading

        executeInScope(
            block = {
                firestore.collection(Constant.SECTION_ITEMS)
                    .get()
                    .addOnSuccessListener { result ->
                        val sectionList = result.map { document ->
                            document.toObject(SectionItem::class.java)
                        }
                        _sectionsState.value = ResultState.Success(data = sectionList)
                    }
                    .addOnFailureListener { exception ->
                        _sectionsState.value = ResultState.Error(exception = exception)
                    }
            },
            onError = { error ->
                _sectionsState.value =
                    ResultState.Error(exception = error as? Exception ?: Exception(error))
            }
        )
    }
}
