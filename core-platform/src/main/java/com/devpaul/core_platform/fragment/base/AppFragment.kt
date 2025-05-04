package com.devpaul.core_platform.fragment.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.devpaul.core_platform.R
import com.devpaul.core_platform.activity.setupActivityScaffold
import com.devpaul.core_platform.fragment.navigate
import com.devpaul.navigation.core.ModularDestination
import com.devpaul.navigation.core.navigateTo
import com.google.android.material.snackbar.Snackbar
import com.devpaul.core_platform.fragment.onBackPressedCallback
import com.devpaul.core_platform.extension.appContext
import com.devpaul.core_platform.extension.viewContainer

abstract class AppFragment : Fragment(), FragmentScaffold {

    private val onBackPressedCallback by onBackPressedCallback()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActivityScaffold()
        onBackPressedCallback?.isEnabled = true
    }

    protected fun DialogFragment.show(tag: String? = null) {
        if (!isAdded) {
            this.show(this@AppFragment.childFragmentManager, tag)
        }
    }

    fun NavDirections.navigate() {
        this@AppFragment.navigate(this)
    }

    protected fun notify(@StringRes message: Int) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    protected fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        action: () -> Any
    ) {
        val snackBar = Snackbar.make(viewContainer, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction(actionText) { _ -> action.invoke() }
        snackBar.setActionTextColor(ContextCompat.getColor(appContext, R.color.colorTextPrimary))
        snackBar.show()
    }

    inline fun <reified T : Any> T.navigate(
        popUpToRoute: ModularDestination? = null,
        inclusive: Boolean = true,
    ) {
        navigateTo<T>(
            destination = this,
            popUpToRoute = popUpToRoute,
            inclusive = inclusive
        )
    }

}