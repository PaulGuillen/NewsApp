package com.devpaul.profile.ui.suggestions

sealed class SuggestionUiIntent {
    data class CreateComment(
        val userId: String,
        val name: String,
        val lastname: String,
        val image: String,
        val comment: String,
    ) : SuggestionUiIntent()

    data class PatchIncrementLike(
        val type: String,
        val commentId: String,
        val userId: String,
        val increment: Boolean,
    ) : SuggestionUiIntent()

    data object GetPost : SuggestionUiIntent()
    data object GetComments : SuggestionUiIntent()
    data object NavigateBack : SuggestionUiIntent()
}