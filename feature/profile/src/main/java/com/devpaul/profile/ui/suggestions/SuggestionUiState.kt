package com.devpaul.profile.ui.suggestions

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.data.datasource.dto.res.CommentResponse
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity

data class SuggestionUiState(
    val createComment: ResultState<CommentEntity> = ResultState.Loading,
    val incrementLike: ResultState<GenericEntity> = ResultState.Loading,
    val posts: ResultState<PostEntity> = ResultState.Loading,
    val getComments: ResultState<GetCommentEntity> = ResultState.Loading,
)