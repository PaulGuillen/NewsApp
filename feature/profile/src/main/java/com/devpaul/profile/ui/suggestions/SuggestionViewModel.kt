package com.devpaul.profile.ui.suggestions

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.domain.usecase.GetPostUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SuggestionViewModel(
    private val getPostUC: GetPostUC,
) : StatefulViewModel<SuggestionUiState, SuggestionUiIntent, SuggestionUiEvent>(
    defaultUIState = {
        SuggestionUiState()
    }
) {

    init {
        SuggestionUiIntent.GetAllPosts.execute()
    }

    override suspend fun onUiIntent(intent: SuggestionUiIntent) {
        when (intent) {
            is SuggestionUiIntent.GetAllPosts -> {
                launchIO {
                    fetchAllPosts()
                }
            }
        }
    }

    private suspend fun fetchAllPosts() {
        updateUiStateOnMain { it.copy(allPosts = ResultState.Loading) }
        val result = getPostUC()
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is GetPostUC.Success.AllPostsSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(allPosts = ResultState.Success(it.allPosts))
                        }
                    }
                }
            }.onFailure<GetPostUC.Failure> {
                when (it) {
                    is GetPostUC.Failure.AllPostsError -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(
                                allPosts = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "An error occurred while fetching all posts."
                                )
                            )
                        }
                    }
                }
            }
    }
}