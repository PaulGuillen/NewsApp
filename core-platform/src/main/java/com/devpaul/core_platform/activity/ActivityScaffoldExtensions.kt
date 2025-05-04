package com.devpaul.core_platform.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.devpaul.core_platform.entity.BottomNavMode
import com.devpaul.core_platform.entity.ToolbarMode
import com.devpaul.core_platform.fragment.base.FragmentScaffold

internal fun Fragment.requireActivityScaffold(): ActivityScaffold {
    val result: FragmentActivity = requireActivity()
    return if (result is ActivityScaffold) {
        result
    } else {
        throw ClassCastException("Activity does not implement ActivityScaffold.")
    }
}

fun Fragment.setToolbarMode(toolbarMode: ToolbarMode){
    requireActivityScaffold().setToolbarMode(toolbarMode)
}

fun Fragment.setBottomNavMode(bottomNavMode: BottomNavMode){
    requireActivityScaffold().setBottomNavMode(bottomNavMode)
}

fun Fragment.setupActivityScaffold(){
    if(this is FragmentScaffold){
        setToolbarMode(toolbarMode())
        setBottomNavMode(bottomNavMode())
    }
}