package com.devpaul.core_platform.extension

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.devpaul.core_platform.activity.AppActivity

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
    beginTransaction().func().commit()

val Fragment.viewContainer: View get() = (activity as AppActivity).fragmentContainer()

val Fragment.appContext: Context get() = activity?.applicationContext!!
