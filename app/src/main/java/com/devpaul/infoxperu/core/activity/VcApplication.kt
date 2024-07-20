package com.devpaul.infoxperu.core.activity

import android.app.Application
import com.devpaul.infoxperu.core.log.TimberFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VcApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        timberInitializer()

    }

    private fun timberInitializer() {
        TimberFactory.setup()
    }
}