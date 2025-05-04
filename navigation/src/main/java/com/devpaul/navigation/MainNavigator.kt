package com.devpaul.navigation

import androidx.fragment.app.Fragment
import com.devpaul.navigation.base.FragmentNavigator

abstract class MainNavigator(
    fragment: Fragment
) : FragmentNavigator(fragment) {
    companion object : NavigatorFactory<MainNavigator>()
}
