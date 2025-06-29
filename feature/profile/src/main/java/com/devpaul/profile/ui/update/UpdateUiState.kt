package com.devpaul.profile.ui.update

import com.devpaul.profile.domain.entity.ProfileUserEntity

data class UpdateUiState(
    val profile: ProfileUserEntity? = null,
)