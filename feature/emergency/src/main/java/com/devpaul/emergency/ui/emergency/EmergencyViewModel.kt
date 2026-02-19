package com.devpaul.emergency.ui.emergency

import com.devpaul.core_domain.entity.Output
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
            is EmergencyUiIntent.GetEmergencyServices -> fetchSection()
            is EmergencyUiIntent.NavigateToDetails -> {
                EmergencyUiEvent.NavigateToDetails(type = intent.type).send()
            }
        }
    }

    private suspend fun fetchSection() {
        updateUiStateOnMain { it.copy(section = ResultState.Loading) }

        when(val result = sectionUC.sectionService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(section = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        section = ResultState.Error(
                            message = result.error.message ?: "Error al cargar la sección de emergencia"
                        )
                    )
                }
            }
        }
    }
}