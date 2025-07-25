package com.devpaul.profile.ui.update

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.domain.entity.GenericEntity

data class UpdateUiState(
    val profile: ProfileUserEntity? = null,
    val updateUser: ResultState<GenericEntity> = ResultState.Idle,
)