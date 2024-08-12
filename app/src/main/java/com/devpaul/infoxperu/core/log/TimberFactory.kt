package com.devpaul.infoxperu.core.log

import com.devpaul.infoxperu.BuildConfig
import timber.log.Timber

object TimberFactory {

    fun setup() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
