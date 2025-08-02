package com.devpaul.profile.ui.suggestions

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.CommentEntity
import com.devpaul.profile.domain.entity.GenericEntity
import com.devpaul.profile.domain.entity.GetCommentEntity
import com.devpaul.profile.domain.entity.PostEntity
import com.devpaul.profile.domain.entity.ProfileUserEntity

data class SuggestionUiState(
    val createComment: ResultState<CommentEntity> = ResultState.Idle,
    val incrementLike: ResultState<GenericEntity> = ResultState.Loading,
    val posts: ResultState<PostEntity> = ResultState.Loading,
    val getComments: ResultState<GetCommentEntity> = ResultState.Loading,
    val profile: ProfileUserEntity? = null,
)