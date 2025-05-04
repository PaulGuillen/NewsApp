package com.devpaul.navigation.core.activity

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.devpaul.navigation.core.ModularDestination
import com.devpaul.navigation.core.ModularGraphBuilder
import com.devpaul.navigation.core.navController
import com.devpaul.navigation.core.modularGraph

abstract class ModularActivity(
    @IdRes val navHostFragmentId: Int
): AppCompatActivity() {

    private val navController by navController(navHostFragmentId)

    fun modularGraph(
        startDestination: ModularDestination,
        builder: ModularGraphBuilder.() -> Unit
    ) {
        navController.modularGraph(
            startDestination = startDestination,
            builder = builder,
        )
    }
}