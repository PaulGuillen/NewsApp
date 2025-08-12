package com.devpaul.emergency.ui.emergency

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.emergency.domain.usecase.SectionUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class EmergencyViewModel(
    private val sectionUC: SectionUC,
) : StatefulViewModel<EmergencyUiState, EmergencyUiIntent, EmergencyUiEvent>(
    defaultUIState = {
        EmergencyUiState()
    }
) {
    init {
        EmergencyUiIntent.GetEmergencyServices.execute()
    }

    override suspend fun onUiIntent(intent: EmergencyUiIntent) {
        when (intent) {
            is EmergencyUiIntent.GetEmergencyServices -> {
                launchConcurrentRequests(
                    { fetchSection() }
                )
            }

            is EmergencyUiIntent.NavigateToDetails -> {
                EmergencyUiEvent.NavigateToDetails(type = intent.type).send()
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
                                        ?: "Error al cargar la sección de emergencia"
                                )
                            )
                        }
                    }
                }
            }
    }
}