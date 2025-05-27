package com.devpaul.home.ui.home

import com.devpaul.core_data.model.Gratitude
import com.devpaul.core_data.model.SectionItem
import com.devpaul.core_data.util.Constant
import com.devpaul.core_data.viewmodel.StatelessViewModel
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import com.devpaul.home.domain.usecase.DollarQuoteUC
import com.devpaul.home.domain.usecase.UITValueUC
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val dollarQuoteUC: DollarQuoteUC,
    private val uitValueUC: UITValueUC,
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

    }

    private fun fetchUit() {

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
                        _gratitudeState.value = ResultState.Success(response = gratitudeList)
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
                        _sectionsState.value = ResultState.Success(response = sectionList)
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