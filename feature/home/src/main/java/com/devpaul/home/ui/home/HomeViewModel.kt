package com.devpaul.home.ui.home

import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.domain.usecase.DollarQuoteUC
import com.devpaul.home.domain.usecase.HolidayAlertUC
import com.devpaul.home.domain.usecase.SeasonUC
import com.devpaul.home.domain.usecase.SectionUC
import com.devpaul.home.domain.usecase.SunatExchangeRateUC
import com.devpaul.home.domain.usecase.UITValueUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
    private val dollarQuoteUC: DollarQuoteUC,
    private val uitValueUC: UITValueUC,
    private val sectionUC: SectionUC,
    private val holidayAlertUC: HolidayAlertUC,
    private val seasonUC: SeasonUC,
    private val sunatExchangeRateUC: SunatExchangeRateUC,
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
                    { fetchSeason() },
                    { fetchHolidayAlert() },
                    { fetchSunatExchangeRate() },
                    { fetchSection() }
                )
            }

            is HomeUiIntent.GetDollarQuote -> launchIO { fetchDollarQuote() }
            is HomeUiIntent.GetUITValue -> launchIO { fetchUit() }
            is HomeUiIntent.GetSections -> launchIO { fetchSection() }
            is HomeUiIntent.ShowEmergencySheet -> showEmergencySheet()
            is HomeUiIntent.HideEmergencySheet -> hideEmergencySheet()
            is HomeUiIntent.DialEmergency -> dialEmergency(intent.number)
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

    private suspend fun fetchHolidayAlert() {
        updateUiStateOnMain { it.copy(holidayAlert = ResultState.Loading) }
        val result = holidayAlertUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is HolidayAlertUC.Success.HolidayAlertSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(holidayAlert = ResultState.Success(it.holidayAlert))
                        }
                    }
                }
            }
            .onFailure<HolidayAlertUC.Failure> {
                when (it) {
                    is HolidayAlertUC.Failure.HolidayAlertError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                holidayAlert = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar alerta de feriados"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchSunatExchangeRate() {
        updateUiStateOnMain { it.copy(sunatExchangeRate = ResultState.Loading) }
        val result = sunatExchangeRateUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is SunatExchangeRateUC.Success.SunatExchangeRateSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(sunatExchangeRate = ResultState.Success(it.exchangeRate))
                        }
                    }
                }
            }
            .onFailure<SunatExchangeRateUC.Failure> {
                when (it) {
                    is SunatExchangeRateUC.Failure.SunatExchangeRateError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                sunatExchangeRate = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar tipo de cambio SUNAT"
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

    private suspend fun fetchSeason() {
        updateUiStateOnMain { it.copy(season = ResultState.Loading) }
        val result = seasonUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is SeasonUC.Success.SeasonSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(season = ResultState.Success(it.season))
                        }
                    }
                }
            }
            .onFailure<SeasonUC.Failure> {
                when (it) {
                    is SeasonUC.Failure.SeasonError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                season = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar las estaciones"
                                )
                            )
                        }
                    }
                }
            }
    }

    private suspend fun fetchSection() {
        updateUiStateOnMain { it.copy(section = ResultState.Loading) }

        when (val result = sectionUC.sectionService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(section = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        section = ResultState.Error(
                            message = result.error.message ?: "Error al cargar las secciones"
                        )
                    )
                }
            }
        }
    }

    private suspend fun showEmergencySheet() {
        updateUiStateOnMain { it.copy(showEmergencySheet = true) }
    }

    private suspend fun hideEmergencySheet() {
        updateUiStateOnMain { it.copy(showEmergencySheet = false) }
    }

    private fun dialEmergency(number: String) {
        HomeUiEvent.DialNumber(number).send()
    }
}
