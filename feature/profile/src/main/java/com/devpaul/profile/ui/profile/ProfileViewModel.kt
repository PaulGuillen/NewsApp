package com.devpaul.profile.ui.profile

import android.content.Intent
import androidx.core.net.toUri
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.util.Constant.USER_UID_KEY
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.domain.usecase.UserProfileUC
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
    private val userProfileUC: UserProfileUC,
) : StatefulViewModel<ProfileUiState, ProfileUiIntent, ProfileUiEvent>(
    defaultUIState = {
        ProfileUiState()
    }
) {

    init {
        ProfileUiIntent.GetUserProfile.execute()
    }

    override suspend fun onUiIntent(intent: ProfileUiIntent) {
        when (intent) {
            is ProfileUiIntent.GetUserProfile -> {
                launchIO { userProfile() }
            }

            is ProfileUiIntent.ShareApp -> {
                sharedApp()
            }

            is ProfileUiIntent.OpenTerms -> {
                openTerms()
            }

            is ProfileUiIntent.OpenPrivacy -> {
                openPrivacy()
            }

            is ProfileUiIntent.Logout -> {
                logOut()
            }
        }
    }

    private fun sharedApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "InfoPerú")
            putExtra(Intent.EXTRA_TEXT, "Descarga InfoPerú desde https://infoperu.app")
        }
        ProfileUiEvent.LaunchIntent(intent = Intent.createChooser(shareIntent, "Compartir vía"))
            .send()
    }


    private fun openTerms() {
        val termsIntent = Intent(Intent.ACTION_VIEW, "https://infoperu.app/terminos".toUri())
        ProfileUiEvent.LaunchIntent(intent = termsIntent).send()
    }


    private fun openPrivacy() {
        val privacyIntent = Intent(Intent.ACTION_VIEW, "https://infoperu.app/privacidad".toUri())
        ProfileUiEvent.LaunchIntent(intent = privacyIntent).send()
    }

    private fun logOut() {
        dataStoreUseCase.setValue(LOG_IN_KEY, false)
        ProfileUiEvent.UserLoggedOut.send()
    }

    private suspend fun userProfile() {
        val uid = dataStoreUseCase.getString(USER_UID_KEY) ?: ""
        val result = userProfileUC(UserProfileUC.Params(uid = uid))
        updateUiStateOnMain { it.copy(profile = ResultState.Loading) }
        result.handleNetworkDefault()
            .onSuccessful {
                when (it) {
                    is UserProfileUC.Success.ProfileSuccess -> {
                        updateUiStateOnMain { uiState ->
                            uiState.copy(profile = ResultState.Success(it.profile))
                        }
                    }
                }
            }
            .onFailure<UserProfileUC.Failure> {
                when (it) {
                    is UserProfileUC.Failure.ProfileError -> {
                        updateUiStateOnMain { uitState ->
                            uitState.copy(
                                profile = ResultState.Error(
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