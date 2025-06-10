package com.devpaul.shared.screen

import androidx.navigation.NavHostController

fun goBackWithReload(navController: NavHostController) {
    navController.previousBackStackEntry
        ?.savedStateHandle
        ?.set("shouldReload", true)

    navController.popBackStack()
}