package com.devpaul.profile.ui.update

import androidx.lifecycle.viewModelScope
import com.devpaul.core_data.serialization.Wrapper
import com.devpaul.core_data.serialization.fromJsonGeneric
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.domain.usecase.UpdateProfileUC
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class UpdateViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val updateProfileUC: UpdateProfileUC,
) : StatefulViewModel<UpdateUiState, UpdateUiIntent, UpdateUiEvent>(
    defaultUIState = {
        UpdateUiState()
    }
) {

    private var originalProfile: ProfileUserEntity? = null

    override suspend fun onUiIntent(intent: UpdateUiIntent) {
        when (intent) {
            is UpdateUiIntent.UpdateProfile -> launchIO {
                updateUserProfile(intent.profileUserEntity)
            }
        }
    }

    suspend fun getProfileData() {
        val profileJson = dataStoreUseCase.getString("profile_data")
        val wrapper: Wrapper<ProfileUserEntity>? = profileJson
            ?.takeIf { it.isNotBlank() }
            ?.let { Gson().fromJsonGeneric(it) }
        val profile = wrapper?.data

        originalProfile = profile

        updateUiStateOnMain {
            it.copy(profile = profile)
        }
    }

    private suspend fun updateUserProfile(userProfile: ProfileUserEntity) {
        val updateUser = UpdateRequest(
            name = userProfile.name,
            lastname = userProfile.lastname,
            birthdate = userProfile.birthdate,
            phone = userProfile.phone,
            email = userProfile.email,
            password = userProfile.password,
            image = userProfile.image ?: "",
        )

        updateUiStateOnMain { it.copy(updateUser = ResultState.Loading) }

        when (
            val result = updateProfileUC.updateProfile(
                uid = userProfile.uid,
                profileUser = updateUser
            )
        ) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(updateUser = ResultState.Success(result.data))
                }
                originalProfile = userProfile
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        updateUser = ResultState.Error(
                            message = result.error.message ?: ERROR_UPDATE_PROFILE,
                        )
                    )
                }
            }
        }
    }

    fun hasProfileChanged(current: ProfileUserEntity): Boolean {
        return current != originalProfile
    }

    fun resetUpdateUser() {
        viewModelScope.launch {
            updateUiStateOnMain { it.copy(updateUser = ResultState.Idle) }
        }
    }

    private companion object {
        const val ERROR_UPDATE_PROFILE = "Ocurrió un error al actualizar el perfil."
    }
}
