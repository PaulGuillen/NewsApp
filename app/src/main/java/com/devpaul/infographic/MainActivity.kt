package com.devpaul.infographic

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.StartIntentSenderForResult
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.SetStatusBarColor
import com.devpaul.infographic.ui.ForceUpdateScreen
import com.devpaul.infographic.ui.openPlayStore
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

sealed class UpdateState {
    data object Loading : UpdateState()
    data object Required : UpdateState()
    data object NotRequired : UpdateState()
}

class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    private val immediateUpdateOptions =
        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()

    private var updateState by mutableStateOf<UpdateState>(UpdateState.Loading)

    private val updatePreferences by lazy {
        getSharedPreferences("mandatory_update", MODE_PRIVATE)
    }

    private val installedVersionCode by lazy {
        PackageInfoCompat.getLongVersionCode(
            packageManager.getPackageInfo(packageName, 0)
        )
    }

    private var validationJob: Job? = null
    private var updateFlowStarted = false

    private val immediateUpdateLauncher =
        registerForActivityResult(StartIntentSenderForResult()) { result ->
            updateFlowStarted = false
            if (result.resultCode != RESULT_OK) {
                showForceUpdateMandatoryMessageAndClose()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appUpdateManager = AppUpdateManagerFactory.create(this)
        enableEdgeToEdge()

        if (isUpdateRequiredForInstalledVersion()) {
            updateState = UpdateState.Required
        }

        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val statusBarColor = if (isDarkTheme) Color.Black else Color.White
            val navController = rememberNavController()

            SetStatusBarColor(
                color = statusBarColor,
                darkIcons = !isDarkTheme
            )

            InfoXPeruTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false,
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (updateState) {
                        UpdateState.Required -> {
                            ForceUpdateScreen(
                                onOpenStore = {
                                    startImmediateUpdate()
                                }
                            )
                        }

                        UpdateState.NotRequired -> {
                            MainGraph(
                                navController = navController
                            )
                        }

                        UpdateState.Loading -> {
                            // Pantalla vacía mientras Google Play valida la actualización
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        validateAppUpdate()
    }

    private fun validateAppUpdate() {
        if (validationJob?.isActive == true) return
        validationJob = lifecycleScope.launch {
            updateState = checkForAppUpdate()
        }
    }

    private fun isUpdateRequiredForInstalledVersion(): Boolean {
        return updatePreferences.getLong("blocked_version_code", -1L) == installedVersionCode
    }

    private fun requireAppUpdate() {
        updatePreferences.edit()
            .putLong("blocked_version_code", installedVersionCode)
            .apply()
        updateState = UpdateState.Required
    }

    private suspend fun checkForAppUpdate(): UpdateState {
        return try {
            val appUpdateInfo = appUpdateManager.appUpdateInfo.await()

            val updateInProgress =
                appUpdateInfo.updateAvailability() ==
                        UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS

            val hasUpdate =
                appUpdateInfo.updateAvailability() ==
                        UpdateAvailability.UPDATE_AVAILABLE

            val immediateAllowed =
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)

            when {
                updateInProgress -> {
                    requireAppUpdate()
                    startImmediateUpdate()
                    UpdateState.Required
                }
                hasUpdate && immediateAllowed -> {
                    requireAppUpdate()
                    UpdateState.Required
                }
                isUpdateRequiredForInstalledVersion() -> UpdateState.Required
                else -> UpdateState.NotRequired
            }
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            if (isUpdateRequiredForInstalledVersion()) {
                UpdateState.Required
            } else {
                UpdateState.NotRequired
            }
        }
    }

    private fun startImmediateUpdate() {
        if (updateFlowStarted) return
        updateFlowStarted = true
        lifecycleScope.launch {
            try {
                val appUpdateInfo = appUpdateManager.appUpdateInfo.await()

                val started = appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    immediateUpdateLauncher,
                    immediateUpdateOptions
                )

                if (!started) {
                    updateFlowStarted = false
                    openPlayStore(this@MainActivity)
                }
            } catch (cancelled: CancellationException) {
                updateFlowStarted = false
                throw cancelled
            } catch (_: Exception) {
                updateFlowStarted = false
                openPlayStore(this@MainActivity)
            }
        }
    }

    private fun showForceUpdateMandatoryMessageAndClose() {
        Toast.makeText(
            this,
            "Debes actualizar la aplicación para continuar.",
            Toast.LENGTH_LONG
        ).show()

        finish()
    }
}
