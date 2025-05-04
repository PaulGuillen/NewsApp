package com.devpaul.core_platform.log

import com.devpaul.core_platform.BuildConfig
import timber.log.Timber

object TimberFactory {

    fun setup() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
