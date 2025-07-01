package com.devpaul.profile.ui.update

import com.devpaul.profile.domain.entity.ProfileUserEntity

sealed class UpdateUiIntent {
    data class UpdateProfile(val profileUserEntity: ProfileUserEntity) : UpdateUiIntent()
}