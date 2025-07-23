package com.devpaul.profile.ui.suggestions

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.PostEntity

data class SuggestionUiState(
    val allPosts: ResultState<PostEntity> = ResultState.Loading,
)