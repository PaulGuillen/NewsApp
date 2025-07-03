package com.devpaul.home.ui.acknowledgments

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
            is AcknowledgmentUiIntent.GetGratitude -> {
                launchIO {
                    fetchGratitude()
                }
            }
        }
    }

    private suspend fun fetchGratitude() {
        updateUiStateOnMain { it.copy(gratitude = ResultState.Loading) }
        val result = gratitudeUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GratitudeUC.Success.GratitudeSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(gratitude = ResultState.Success(it.gratitude))
                        }
                    }
                }
            }
            .onFailure<GratitudeUC.Failure> {
                when (it) {
                    is GratitudeUC.Failure.GratitudeError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                gratitude = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar los agradecimientos"
                                )
                            )
                        }
                    }
                }
            }
    }

}