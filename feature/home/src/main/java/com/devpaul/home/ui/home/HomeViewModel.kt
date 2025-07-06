package com.devpaul.home.ui.home

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.domain.usecase.DollarQuoteUC
import com.devpaul.home.domain.usecase.SectionUC
import com.devpaul.home.domain.usecase.UITValueUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val dollarQuoteUC: DollarQuoteUC,
    private val uitValueUC: UITValueUC,
    private val sectionUC: SectionUC,
) : StatefulViewModel<HomeUiState, HomeUiIntent, HomeUiEvent>(
    defaultUIState = {
        HomeUiState()
    }
) {
    init {
        HomeUiIntent.GetHomeServices.execute()
    }

    override suspend fun onUiIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.GetHomeServices -> {
                launchConcurrentRequests(
                    { fetchDollarQuote() },
                    { fetchUit() },
                    { fetchSection() }
                )
            }

            is HomeUiIntent.GetDollarQuote -> {
                launchIO {
                    fetchDollarQuote()
                }
            }

            is HomeUiIntent.GetUITValue -> {
                launchIO {
                    fetchUit()
                }
            }
        }
    }

    private suspend fun fetchDollarQuote() {
        updateUiStateOnMain { it.copy(dollarQuote = ResultState.Loading) }
        val result = dollarQuoteUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is DollarQuoteUC.Success.DollarQuoteSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(dollarQuote = ResultState.Success(it.dollarQuote))
                        }
                    }
                }
            }
            .onFailure<DollarQuoteUC.Failure> {
                when (it) {
                    is DollarQuoteUC.Failure.DollarQuoteError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                dollarQuote = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar el valor del dólar"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchUit() {
        updateUiStateOnMain { it.copy(uitValue = ResultState.Loading) }
        val result = uitValueUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is UITValueUC.Success.UITSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(uitValue = ResultState.Success(it.uit))
                        }
                    }
                }
            }
            .onFailure<UITValueUC.Failure> {
                when (it) {
                    is UITValueUC.Failure.UITError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                uitValue = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar el valor de la UIT"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchSection() {
        updateUiStateOnMain { it.copy(section = ResultState.Loading) }
        val result = sectionUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is SectionUC.Success.SectionSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(section = ResultState.Success(it.section))
                        }
                    }
                }
            }
            .onFailure<SectionUC.Failure> {
                when (it) {
                    is SectionUC.Failure.SectionError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                section = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar las secciones"
                                )
                            )
                        }
                    }
                }
            }
    }

}