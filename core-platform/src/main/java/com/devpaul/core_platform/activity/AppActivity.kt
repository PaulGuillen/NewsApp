package com.devpaul.core_platform.activity

import android.view.View
import androidx.annotation.IdRes
import com.devpaul.navigation.core.activity.ModularActivity

abstract class AppActivity(
    @IdRes navHostFragmentId: Int
): ModularActivity(navHostFragmentId) {

    abstract fun fragmentContainer(): View
}