package com.devpaul.profile.ui.profile

import com.devpaul.core_platform.extension.ResultState
import com.devpaul.profile.domain.entity.ProfileEntity

data class ProfileUiState(
    val profile: ResultState<ProfileEntity>? = null,
)