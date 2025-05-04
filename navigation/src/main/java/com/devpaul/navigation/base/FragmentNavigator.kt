package com.devpaul.navigation.base

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

abstract class FragmentNavigator(
    private val fragment: Fragment
) {

    protected val navController: NavController by lazy {
        fragment.findNavController()
    }

    protected fun createNavOptions(
        @IdRes popUpToId: Int?,
        inclusive: Boolean
    ): NavOptions {
        val builder = NavOptions.Builder()
        if (popUpToId != null) {
            builder.setPopUpTo(
                destinationId = popUpToId,
                inclusive = inclusive
            )
        }
        return builder.build()
    }
}