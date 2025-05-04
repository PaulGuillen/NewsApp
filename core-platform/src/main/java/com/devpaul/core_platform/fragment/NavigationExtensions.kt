package com.devpaul.core_platform.fragment

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.R
import com.devpaul.core_platform.entity.DialogInteractionListener
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import com.devpaul.navigation.core.getSafeArgs
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.properties.ReadOnlyProperty

fun Fragment.navigate(directions: NavDirections) {
    try {
        findNavController().navigate(directions)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.navigate(@IdRes resId: Int) {
    try {
        findNavController().navigate(resId)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.popBackStack() {
    try {
        findNavController().popBackStack()
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun Fragment.popBackStack(@IdRes destinationId: Int, inclusive: Boolean = false) {
    try {
        findNavController().popBackStack(destinationId, inclusive)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
}

fun <T : DialogFragment> T.withListener(listener: DialogInteractionListener, tag: String? = null): T {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            if (this@withListener is DialogInteractionListener) {
                listener.onDialogAccept(tag)
            }
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            this@withListener.lifecycle.removeObserver(this)
            listener.onDialogCancel(tag)
        }
    })
    return this
}

fun Fragment.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    lifecycleScope.launch(
        block = block,
        context = context
    )
}

fun <T: DialogFragment> T.isCancelable(isCancelable: Boolean): T {
    this.isCancelable = isCancelable
    return this
}

fun <T: DialogFragment> T.fullscreen(): T {
    setStyle(
        DialogFragment.STYLE_NORMAL,
        R.style.ShapeAppearanceOverlay_MaterialComponents_MaterialCalendar_Window_Fullscreen
    )
    return this
}

fun <T: Fragment> T.putArgs(vararg pairs: Pair<String, Any?>): T {
    arguments = bundleOf(*pairs)
    return this
}

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun <T> argument(
    key: String,
    defaultValue: T? = null
): ReadOnlyProperty<Fragment, T> {
    return ReadOnlyProperty { thisRef, _ ->
        (thisRef.arguments?.get(key) ?: defaultValue) as T
    }
}

inline fun <reified Args : NavArgs> StatefulViewModel<*, *, *>.getArgs(): Args {
    return Args::class.java.getMethod(
        "fromSavedStateHandle",
        SavedStateHandle::class.java
    ).invoke(null, savedStateHandle!!) as Args
}

inline fun <reified Args> StatefulViewModel<*, *, *>.getSafeArgs(): Args? {
    return savedStateHandle?.getSafeArgs()
}
