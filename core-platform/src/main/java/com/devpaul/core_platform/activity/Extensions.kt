package com.devpaul.core_platform.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.devpaul.core_platform.lifecycle.base.ViewModelLoadable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal fun Fragment.requireLoadingActivity(): LoadingActivity {
    val result: FragmentActivity = requireActivity()
    return if (result is LoadingActivity) {
        result
    } else {
        throw ClassCastException("Activity does not implement LoadingActivity.")
    }
}

suspend fun Fragment.showLoading(timeMillis: Long) {
    isLoading = true
    delay(timeMillis)
    isLoading = false
}

var Fragment.isLoading: Boolean
    get() = requireLoadingActivity().isLoading
    set(value) {
        if (value && !isLoading) {
            requireLoadingActivity().isLoading = true
        } else if (!value && isLoading) {
            requireLoadingActivity().isLoading = false
        }
    }

fun Fragment.handleLoading(viewModel: ViewModelLoadable){
    viewLifecycleOwner.lifecycleScope.launch {
        try {
            viewModel.onLoading {
                isLoading = it
            }
        } finally {
            if(!viewModel.isLoading){
                isLoading = false
            }
        }
    }
}