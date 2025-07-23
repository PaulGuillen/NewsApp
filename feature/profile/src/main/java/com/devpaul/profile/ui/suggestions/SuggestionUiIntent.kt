package com.devpaul.profile.ui.suggestions

sealed class SuggestionUiIntent {
    data object GetAllPosts : SuggestionUiIntent()
}