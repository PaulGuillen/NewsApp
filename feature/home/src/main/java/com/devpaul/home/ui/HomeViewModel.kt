package com.devpaul.home.ui

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.domain.usecase.DollarQuoteUC
import com.devpaul.home.domain.usecase.GratitudeUC
import com.devpaul.home.domain.usecase.SectionUC
import com.devpaul.home.domain.usecase.UITValueUC
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

    init {
        HomeUiIntent.GetDollarQuote.execute()
        HomeUiIntent.GetUIT.execute()
        HomeUiIntent.GetGratitude.execute()
        HomeUiIntent.GetSection.execute()
    }

    override suspend fun onUiIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.GetDollarQuote -> launchIO { fetchDollarQuote() }
            is HomeUiIntent.GetUIT -> launchIO { fetchUit() }
            is HomeUiIntent.GetGratitude -> launchIO { fetchGratitude() }
            is HomeUiIntent.GetSection -> launchIO { fetchSection() }

        }
    }

    private suspend fun fetchDollarQuote() {
        updateUiStateOnMain { it.copy(isDollarQuoteLoading = true) }
        val result = dollarQuoteUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is DollarQuoteUC.Success.DollarQuoteSuccess -> {
                        HomeUiEvent.DollarQuoteSuccess(it.dollarQuote).send()
                    }
                }
            }
            .onFailure<DollarQuoteUC.Failure> {
                when (it) {
                    is DollarQuoteUC.Failure.DollarQuoteError -> {
                        HomeUiEvent.DollarQuoteError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isDollarQuoteLoading = false) }
            }
    }

    private suspend fun fetchUit() {
        updateUiStateOnMain { it.copy(isUITLoading = true) }
        val result = uitValueUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is UITValueUC.Success.UITSuccess -> {
                        HomeUiEvent.UITSuccess(it.uit).send()
                    }
                }
            }
            .onFailure<UITValueUC.Failure> {
                when (it) {
                    is UITValueUC.Failure.UITError -> {
                        HomeUiEvent.UITError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isUITLoading = false) }
            }
    }

    private suspend fun fetchSection() {
        updateUiStateOnMain { it.copy(isSectionsLoading = true) }
        val result = sectionUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is SectionUC.Success.SectionSuccess -> {
                        HomeUiEvent.SectionSuccess(it.section).send()
                    }
                }
            }
            .onFailure<SectionUC.Failure> {
                when (it) {
                    is SectionUC.Failure.SectionError -> {
                        HomeUiEvent.SectionError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isSectionsLoading = false) }
            }
    }

    private suspend fun fetchGratitude() {
        updateUiStateOnMain { it.copy(isGratitudeLoading = true) }
        val result = gratitudeUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GratitudeUC.Success.GratitudeSuccess -> {
                        HomeUiEvent.GratitudeSuccess(it.gratitude).send()
                    }
                }
            }
            .onFailure<GratitudeUC.Failure> {
                when (it) {
                    is GratitudeUC.Failure.GratitudeError -> {
                        HomeUiEvent.GratitudeError(it.error).send()
                    }
                }
            }
            .also {
                updateUiStateOnMain { it.copy(isGratitudeLoading = false) }
            }
    }
}