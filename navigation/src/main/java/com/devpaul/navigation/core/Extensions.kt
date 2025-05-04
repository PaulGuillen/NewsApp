package com.devpaul.navigation.core

import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.devpaul.navigation.core.internal.ModularGraphBuilderImpl
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

// Activity Extensions

fun FragmentActivity.navController(@IdRes id: Int): Lazy<NavController> = lazy {
    val navHostFragment = supportFragmentManager.findFragmentById(id) as NavHostFragment
    navHostFragment.navController
}

//Navigation ceator extensions

inline fun <reified T> destinationOf(): ModularDestination {
    return ModularDestination(
        kSerializer = Json.serializersModule.serializer<T>()
    )
}

fun NavController.modularGraph(
    startDestination: ModularDestination,
    builder: ModularGraphBuilder.() -> Unit
) {
    val builderScope = ModularGraphBuilderImpl(
        navController = this,
        startDestination = startDestination
    )
    graph = builderScope.apply(builder).build()
}

inline fun <reified F: Fragment, reified Args> ModularGraphBuilder.fragment() {
    createFragment(
        fragment = F::class,
        destination = destinationOf<Args>()
    )
}

inline fun <reified F: DialogFragment, reified Args> ModularGraphBuilder.dialog() {
    createDialog(
        dialog = F::class,
        destination = destinationOf<Args>()
    )
}

inline fun <reified Args> ModularGraphBuilder.navigation(
    startDestination: ModularDestination,
    noinline builder: ModularGraphBuilder.() -> Unit,
) {
    createNavigation(
        startDestination = startDestination,
        route = destinationOf<Args>(),
        builder = builder
    )
}

// Fragment Extensions

inline fun <reified T: Any> Fragment.navigateTo(
    destination: T,
    popUpToRoute: ModularDestination? = null,
    inclusive: Boolean = true,
) {
    val modularDestination = destinationOf<T>()
    val route = modularDestination.createSafeArgs(value = destination)
    val builder = NavOptions.Builder()
    if (popUpToRoute != null) {
        builder.setPopUpTo(
            route = popUpToRoute.asString(),
            inclusive = inclusive
        )
    }
    try {
        findNavController().navigate(
            route = route,
            navOptions = builder.build()
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.popBackStack(
    destination: ModularDestination,
    inclusive: Boolean = false
) {
    try {
        findNavController().popBackStack(
            route = destination.route,
            inclusive = inclusive
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.popBackStack() {
    try {
        findNavController().popBackStack()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

inline fun <reified T: Any> Fragment.safeArgs(): Lazy<T> = lazy {
    requireArguments().getSafeArgs()
}
