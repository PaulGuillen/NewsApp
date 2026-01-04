package com.devpaul.home.ui.acknowledgments

import com.devpaul.core_domain.entity.Output
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.home.domain.usecase.GratitudeUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AcknowledgmentViewModel(
    private val gratitudeUC: GratitudeUC
) : StatefulViewModel<AcknowledgmentUiState, AcknowledgmentUiIntent, AcknowledgmentUiEvent>(
    defaultUIState = {
        AcknowledgmentUiState()
    }
) {
    init {
        AcknowledgmentUiIntent.GetGratitude.execute()
    }

    override suspend fun onUiIntent(intent: AcknowledgmentUiIntent) {
        when (intent) {
            is AcknowledgmentUiIntent.GetGratitude -> launchIO {fetchGratitude()}
        }
    }

    private suspend fun fetchGratitude() {
        updateUiStateOnMain { it.copy(gratitude = ResultState.Loading) }

        when (val result = gratitudeUC.gratitudeService()) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(gratitude = ResultState.Success(result.data))
                }
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        gratitude = ResultState.Error(
                            message = result.error.message ?: "Error al cargar los agradecimientos"
                        ))
                }
            }
        }
    }
}