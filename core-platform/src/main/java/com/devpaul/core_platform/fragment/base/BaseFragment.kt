package com.devpaul.core_platform.fragment.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

open class BaseFragment : DialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}