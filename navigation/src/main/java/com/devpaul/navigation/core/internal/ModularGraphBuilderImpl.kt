package com.devpaul.navigation.core.internal

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.DialogFragmentNavigatorDestinationBuilder
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder
import androidx.navigation.get
import com.devpaul.navigation.core.Constants
import com.devpaul.navigation.core.ModularDestination
import com.devpaul.navigation.core.ModularGraphBuilder
import kotlin.reflect.KClass

internal class ModularGraphBuilderImpl(
    private val navController: NavController,
    startDestination: ModularDestination,
    route: ModularDestination? = null,
): ModularGraphBuilder {

    private val navGraphBuilder = NavGraphBuilder(
        provider = navController.navigatorProvider,
        startDestination = startDestination.route,
        route = route?.route
    )

    override fun createFragment(
        destination: ModularDestination,
        fragment: KClass<out Fragment>,
    ) {
        val builderDestination = FragmentNavigatorDestinationBuilder(
            navGraphBuilder.provider[FragmentNavigator::class],
            destination.route,
            fragment
        )
        builderDestination.argument(name = Constants.ARG_NAME) {
            type = NavType.StringType
            defaultValue = ""
        }
        navGraphBuilder.destination(builderDestination)
    }

    override fun createDialog(
        destination: ModularDestination,
        dialog: KClass<out DialogFragment>
    ) {
        val builderDestination = DialogFragmentNavigatorDestinationBuilder(
            navGraphBuilder.provider[DialogFragmentNavigator::class],
            destination.route,
            dialog
        )
        builderDestination.argument(name = Constants.ARG_NAME) {
            type = NavType.StringType
            defaultValue = ""
        }
        navGraphBuilder.destination(builderDestination)
    }

    override fun createNavigation(
        startDestination: ModularDestination,
        route: ModularDestination,
        builder: ModularGraphBuilder.() -> Unit
    ) {
        val builderScope = ModularGraphBuilderImpl(
            navController = navController,
            startDestination = startDestination,
            route = route
        )
        val navGraph = builderScope.apply(builder).build()
        navGraphBuilder.addDestination(navGraph)
    }

    override fun build(): NavGraph {
        return navGraphBuilder.build()
    }
}