package com.devpaul.profile.ui.update

import com.devpaul.core_data.serialization.Wrapper
import com.devpaul.core_data.serialization.fromJsonGeneric

import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.data.datasource.dto.req.UpdateRequest
import com.devpaul.profile.domain.entity.ProfileUserEntity
import com.devpaul.profile.domain.usecase.UpdateProfileUC
import com.google.gson.Gson
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

    override suspend fun onUiIntent(intent: UpdateUiIntent) {
        when (intent) {
            is UpdateUiIntent.UpdateProfile -> {
                updateUserProfile(intent.profileUserEntity)
            }
        }
    }

    suspend fun getProfileData() {
        val profileJson = dataStoreUseCase.getString("profile_data")
        val wrapper: Wrapper<ProfileUserEntity>? = Gson().fromJsonGeneric(profileJson)
        val profile = wrapper?.data

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
            image = userProfile.image ?: ""
        )
        val result =
            updateProfileUC(UpdateProfileUC.Params(uid = userProfile.uid, profileUser = updateUser))
        updateUiStateOnMain { it.copy(updateUser = ResultState.Loading) }
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is UpdateProfileUC.Success.UpdateSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(updateUser = ResultState.Success(it.updateUser))
                        }
                    }
                }
            }
            .onFailure<UpdateProfileUC.Failure> {
                when (it) {
                    is UpdateProfileUC.Failure.UpdateError -> {
                        updateUiStateOnMain { uitState ->
                            uitState.copy(
                                updateUser = ResultState.Error(
                                    message = it.error.apiErrorResponse?.message
                                        ?: "Error al cargar el perfil"
                                )
                            )
                        }
                    }
                }
            }
    }
}