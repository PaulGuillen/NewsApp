package com.devpaul.profile.ui.update

import android.net.Uri
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.google.gson.Gson
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UpdateViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
) : StatefulViewModel<UpdateUiState, UpdateUiIntent, UpdateUiEvent>(
    defaultUIState = {
        UpdateUiState()
    }
) {

    override suspend fun onUiIntent(intent: UpdateUiIntent) {

    }


    suspend fun getProfileData(profileData: String?) {
        val gson = Gson()
        val decodedProfileJson = profileData?.let { Uri.decode(it) }
        val profileEntity = decodedProfileJson?.let {
            gson.fromJson(it, ProfileUserEntity::class.java)
        }

        updateUiStateOnMain {
            it.copy(profile = profileEntity)
        }
    }

}