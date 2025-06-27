package com.devpaul.profile.ui.profile

import android.content.Intent
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.devpaul.core_data.util.Constant.LOG_IN_KEY
import com.devpaul.core_domain.use_case.DataStoreUseCase
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class ProfileViewModel(
    private val dataStoreUseCase: DataStoreUseCase,
) : StatefulViewModel<ProfileUiState, ProfileUiIntent, ProfileUiEvent>(
    defaultUIState = {
        ProfileUiState()
    }
) {
    init {

    }

    override suspend fun onUiIntent(intent: ProfileUiIntent) {
        when (intent) {
            ProfileUiIntent.ShareApp -> {
                sharedApp()
            }

            ProfileUiIntent.OpenTerms -> {
                openTerms()
            }

            ProfileUiIntent.OpenPrivacy -> {
                openPrivacy()
            }

            ProfileUiIntent.Logout -> {
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
}