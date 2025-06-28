package com.devpaul.profile.ui.update

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.ProfileEntity

data class UpdateUiState(
    val profile: ResultState<ProfileEntity>? = ResultState.Loading,
)