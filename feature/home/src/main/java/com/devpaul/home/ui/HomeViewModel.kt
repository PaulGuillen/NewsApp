package com.devpaul.home.ui

import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.domain.entity.DollarQuoteEntity
import com.devpaul.home.domain.entity.GratitudeEntity
import com.devpaul.home.domain.entity.SectionEntity
import com.devpaul.home.domain.entity.UITEntity
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
        HomeUiIntent.GetHomeServices.execute()
    }

    override suspend fun onUiIntent(intent: HomeUiIntent) {
        when (intent) {
            is HomeUiIntent.GetHomeServices -> {
                launchConcurrentRequests(
                    { fetchDollarQuote() },
                    { fetchUit() },
                    { fetchSection() },
                    { fetchGratitude() }
                )
            }
        }
    }

    private suspend fun fetchDollarQuote() {
        runWithUiStateUpdate(
            onLoading = {
                updateUiStateOnMain { it.copy(dollarQuote = ResultState.Loading) }
            },
            block = {
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
            },
            onError = { e ->
                updateUiStateOnMain {
                    it.copy(dollarQuote = ResultState.Error("Error inesperado: ${e.localizedMessage}"))
                }
            }
        )
    }

    private suspend fun fetchUit() {
        runWithUiStateUpdate(
            onLoading = {
                updateUiStateOnMain { it.copy(uitValue = ResultState.Loading) }
            },
            block = {
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
            },
            onError = { e ->
                updateUiStateOnMain {
                    it.copy(uitValue = ResultState.Error("Error inesperado: ${e.localizedMessage}"))
                }
            }
        )
    }

    private suspend fun fetchSection() {
        runWithUiStateUpdate(
            onLoading = {
                updateUiStateOnMain { it.copy(section = ResultState.Loading) }
            },
            block = {
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
            },
            onError = { e ->
                updateUiStateOnMain {
                    it.copy(section = ResultState.Error("Error inesperado: ${e.localizedMessage}"))
                }
            }
        )
    }

    private suspend fun fetchGratitude() {
        runWithUiStateUpdate(
            onLoading = {
                updateUiStateOnMain { it.copy(gratitude = ResultState.Loading) }
            },
            block = {
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
            },
            onError = { e ->
                updateUiStateOnMain {
                    it.copy(gratitude = ResultState.Error("Error inesperado: ${e.localizedMessage}"))
                }
            }
        )
    }

    suspend fun openDollarQuote(response: DollarQuoteEntity) {
        updateUiStateOnMain {
            it.copy(dollarQuote = ResultState.Success(response))
        }
    }

    suspend fun openUIT(response: UITEntity) {
        updateUiStateOnMain {
            it.copy(uitValue = ResultState.Success(response))
        }
    }

    suspend fun openGratitude(response: GratitudeEntity) {
        updateUiStateOnMain {
            it.copy(gratitude = ResultState.Success(response))
        }
    }

    suspend fun openSection(response: SectionEntity) {
        updateUiStateOnMain {
            it.copy(section = ResultState.Success(response))
        }
    }

    suspend fun handleErrorEvents(type: String, error: Defaults.HttpError<String>) {
        when (type) {
            "dollarQuote" -> {
               updateUiStateOnMain {
                     it.copy(dollarQuote = ResultState.Error(error.apiErrorResponse?.message ?: "Error inesperado"))
               }
            }
            "uit" -> {
                updateUiStateOnMain {
                    it.copy(uitValue = ResultState.Error(error.apiErrorResponse?.message ?: "Error inesperado"))
                }
            }
            "gratitude" -> {
                updateUiStateOnMain {
                    it.copy(gratitude = ResultState.Error(error.apiErrorResponse?.message ?: "Error inesperado"))
                }
            }
            "section" -> {
                updateUiStateOnMain {
                    it.copy(section = ResultState.Error(error.apiErrorResponse?.message ?: "Error inesperado"))
                }
            }
        }
    }

}