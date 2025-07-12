package com.devpaul.profile.ui.suggestions

import com.devpaul.core_platform.lifecycle.StatefulViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SuggestionViewModel(

): StatefulViewModel<SuggestionUiState, SuggestionUiIntent, SuggestionUiEvent>(
    defaultUIState = {
        SuggestionUiState()
    }
) {
    override suspend fun onUiIntent(intent: SuggestionUiIntent) {
    }


}