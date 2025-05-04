package com.devpaul.core_platform.fragment.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.devpaul.core_platform.fragment.onBackPressedCallback
import com.devpaul.core_platform.fragment.navigate

abstract class AppBottomSheet: BottomSheetDialogFragment() {

    private val onBackPressedCallback by onBackPressedCallback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressedCallback?.isEnabled = true
    }

    protected fun DialogFragment.show(){
        if(!isAdded){
            this.show(this@AppBottomSheet.childFragmentManager, null)
        }
    }

    fun NavDirections.navigate(){
        this@AppBottomSheet.navigate(this)
    }

}