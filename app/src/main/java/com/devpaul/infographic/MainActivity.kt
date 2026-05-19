package com.devpaul.infographic

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.devpaul.core_platform.theme.InfoXPeruTheme
import com.devpaul.core_platform.theme.SetStatusBarColor
import com.devpaul.infographic.ui.ForceUpdateScreen
import com.devpaul.infographic.ui.openPlayStore
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

class MainActivity : ComponentActivity() {

    private lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appUpdateManager = AppUpdateManagerFactory.create(this)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = isSystemInDarkTheme()
            val statusBarColor = if (isDarkTheme) Color.Black else Color.White

            SetStatusBarColor(color = statusBarColor, darkIcons = !isDarkTheme)

            val navController = rememberNavController()
            val showForceUpdate = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                checkForAppUpdate(onUpdateAvailable = { showForceUpdate.value = true } )
            }

            InfoXPeruTheme(
                darkTheme = isDarkTheme,
                dynamicColor = false,
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (showForceUpdate.value) {
                        ForceUpdateScreen(
                            onOpenStore = {
                                startImmediateUpdate()
                            }
                        )
                    } else {
                        MainGraph(
                            navController = navController
                        )
                    }
                }
            }
        }
    }

    private fun checkForAppUpdate(
        onUpdateAvailable: () -> Unit
    ) {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                val hasUpdate =
                    appUpdateInfo.updateAvailability() ==
                            UpdateAvailability.UPDATE_AVAILABLE

                val immediateAllowed =
                    appUpdateInfo.isUpdateTypeAllowed(
                        AppUpdateType.IMMEDIATE
                    )

                if (hasUpdate && immediateAllowed) {
                    onUpdateAvailable()
                }
            }
    }

    private fun startImmediateUpdate() {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                try {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        REQUEST_CODE_APP_UPDATE
                    )
                } catch (_: IntentSender.SendIntentException) {
                    openPlayStore(this)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->

                val updateInProgress =
                    appUpdateInfo.updateAvailability() ==
                            UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS

                if (updateInProgress) {
                    startImmediateUpdate()
                }
            }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (
            requestCode == REQUEST_CODE_APP_UPDATE &&
            resultCode != RESULT_OK
        ) {
            Toast.makeText(
                this,
                "Debes actualizar la aplicación para continuar.",
                Toast.LENGTH_LONG
            ).show()
            finish()
        }
    }

    companion object {
        private const val REQUEST_CODE_APP_UPDATE = 500
    }
}