package com.devpaul.profile.ui.profile

import android.content.Intent
import androidx.core.net.toUri
import com.devpaul.core_data.serialization.Wrapper
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_data.util.Constant.USER_UID_KEY
import com.devpaul.core_domain.entity.Output
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.extension.ResultState
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.profile.domain.usecase.UserProfileUC
import com.google.gson.Gson
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
            is ProfileUiIntent.GetUserProfile -> launchIO { userProfile() }
            is ProfileUiIntent.ShareApp -> sharedApp()
            is ProfileUiIntent.OpenTerms -> openTerms()
            is ProfileUiIntent.OpenPrivacy -> openPrivacy()
            is ProfileUiIntent.Logout -> logOut()
        }
    }

    private suspend fun userProfile() {
        updateUiStateOnMain { it.copy(profile = ResultState.Loading) }

        val uid = dataStoreUseCase.getString(USER_UID_KEY).orEmpty()

        when (val result = userProfileUC.profileById(uid)) {
            is Output.Success -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(profile = ResultState.Success(result.data))
                }

                val profileJson = Gson().toJson(Wrapper(result.data.data))
                dataStoreUseCase.setValue("profile_data", profileJson)
            }

            is Output.Failure -> {
                updateUiStateOnMain { uiState ->
                    uiState.copy(
                        profile = ResultState.Error(
                            message = result.error.message ?: ERROR_PROFILE
                        )
                    )
                }
            }
        }
    }

    private fun sharedApp() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "InfoPerÃº")
            putExtra(Intent.EXTRA_TEXT, "Descarga InfoPerÃº desde https://infoperu.app")
        }
        ProfileUiEvent.LaunchIntent(intent = Intent.createChooser(shareIntent, "Compartir vÃ­a"))
            .send()
    }

    private fun openTerms() {
        val termsIntent = Intent(Intent.ACTION_VIEW, "https://paulguillen.github.io/Think-Terms-Conditions/".toUri())
        ProfileUiEvent.LaunchIntent(intent = termsIntent).send()
    }

    private fun openPrivacy() {
        val privacyIntent = Intent(Intent.ACTION_VIEW, "https://paulguillen.github.io/Think-Privacy/".toUri())
        ProfileUiEvent.LaunchIntent(intent = privacyIntent).send()
    }

    private fun logOut() {
        dataStoreUseCase.setValue(LOG_IN_KEY, false)
        ProfileUiEvent.UserLoggedOut.send()
    }

    private companion object {
        const val ERROR_PROFILE = "Error al cargar el perfil"
    }
}
