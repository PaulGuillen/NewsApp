package com.devpaul.infoxperu.core.log

import android.os.Build
import timber.log.Timber
//import com.devpaul.infoxperu.BuildConfig

object TimberFactory {

    fun setup() {
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }
}
