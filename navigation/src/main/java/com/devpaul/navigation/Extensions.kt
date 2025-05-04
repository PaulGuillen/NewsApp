package com.devpaul.navigation

import androidx.fragment.app.Fragment

fun <F> Fragment.navigator(factory: NavigatorFactory<F>) = lazy {
    factory.create(this)
}

fun Fragment.mainNavigator() = navigator(MainNavigator.Companion)