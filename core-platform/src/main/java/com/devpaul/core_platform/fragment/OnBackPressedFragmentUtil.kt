package com.devpaul.core_platform.fragment

import androidx.activity.ComponentDialog
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.devpaul.core_platform.fragment.base.OnBackPressedFragment

fun Fragment.onBackPressedCallback(): Lazy<OnBackPressedCallback?>{
    return object: Lazy<OnBackPressedCallback?> {

        private var isInit: Boolean = false
        private var _value: OnBackPressedCallback? = null
        override val value: OnBackPressedCallback?
            get() {
                if (!isInit) {
                    isInit = true
                    val fragment = this@onBackPressedCallback
                    if (fragment is OnBackPressedFragment) {
                        val callback = object : OnBackPressedCallback(false) {
                            override fun handleOnBackPressed() {
                                isEnabled = false
                                fragment.onBackPressed()
                                isEnabled = true
                            }
                        }
                        _value = callback
                        when(fragment) {
                            is DialogFragment -> when(val dialog = fragment.dialog) {
                                is BottomSheetDialog -> {
                                    dialog.onBackPressedDispatcher
                                        .addCallback(fragment, callback)
                                }
                                is ComponentDialog -> {
                                    dialog.onBackPressedDispatcher
                                        .addCallback(fragment, callback)
                                }
                                else -> Unit
                            }
                            else -> {
                                fragment.requireActivity().onBackPressedDispatcher
                                    .addCallback(fragment, callback)
                            }
                        }
                    }
                }
                return _value
            }

        override fun isInitialized() = true

    }
}

/**
 * Llama al método [onBackPressed] del fragmento actual si dicho fragmento implementa la interfaz [OnBackPressedFragment].
 * Este método permite activar manualmente el comportamiento personalizado de retroceso del fragmento.
 */
fun <T: OnBackPressedFragment> T.callOnBackPressed(){
    onBackPressed()
}
