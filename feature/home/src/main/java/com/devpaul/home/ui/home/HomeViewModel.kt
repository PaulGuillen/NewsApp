package com.devpaul.home.ui.home

import com.devpaul.core_data.model.Gratitude
import com.devpaul.core_data.model.SectionItem
import com.devpaul.core_data.viewmodel.StatelessViewModel
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.data.datasource.dto.response.DollarQuoteResponse
import com.devpaul.home.data.datasource.dto.response.UITResponse
import com.devpaul.home.domain.usecase.DollarQuoteUC
import com.devpaul.home.domain.usecase.GratitudeUC
import com.devpaul.home.domain.usecase.SectionUC
import com.devpaul.home.domain.usecase.UITValueUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val dollarQuoteUC: DollarQuoteUC,
    private val uitValueUC: UITValueUC,
    private val sectionUC: SectionUC,
    private val gratitudeUC: GratitudeUC,
) : StatefulViewModel<HomeUiState, HomeUiIntent, HomeUiEvent>(
    defaultUIState = {
        HomeUiState()
    }
) {

    override suspend fun onUiIntent(intent: HomeUiIntent) {
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

    private fun fetchSections() {

    }

    private fun fetchGratitude() {

    }


}