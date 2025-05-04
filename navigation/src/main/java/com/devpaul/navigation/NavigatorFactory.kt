package com.devpaul.navigation

import androidx.fragment.app.Fragment

abstract class NavigatorFactory<N> {

    private lateinit var factory: (Fragment) -> N

    operator fun invoke(factory: (Fragment) -> N) {
        this.factory = factory
    }

    fun create(fragment: Fragment): N = factory(fragment)
}