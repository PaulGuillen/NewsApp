package com.devpaul.profile.ui.suggestions

sealed class SuggestionUiEvent {
    data object NavigationBack : SuggestionUiEvent()
}