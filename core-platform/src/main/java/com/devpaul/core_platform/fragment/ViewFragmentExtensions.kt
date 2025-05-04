package com.devpaul.core_platform.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.devpaul.core_domain.entity.Defaults
import com.devpaul.core_platform.lifecycle.StatelessViewModel
import com.devpaul.core_platform.lifecycle.base.UiEventHolder
import com.devpaul.core_platform.lifecycle.base.UiStateHolder
import com.devpaul.core_platform.lifecycle.base.ViewModelLoadable
import com.devpaul.core_platform.activity.handleLoading
import com.devpaul.core_platform.lifecycle.StatefulViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

inline fun <reified VM: ViewModel> Fragment.createViewModel(
    navGraphId: Int? = null
): Lazy<VM> {
    return if (navGraphId == null) {
        viewModel()
    } else {
        koinNavGraphViewModel(
            navGraphId = navGraphId
        )
    }
}

fun <E, VM> ViewFragment.Stateful<*, VM>.onUiState(
    listenWhen: (lastUiState: E?, newUiState: E) -> Boolean = { _, _ -> true },
    block: (uiState: E) -> Unit
) where VM: ViewModel, VM: UiStateHolder<E> {
    viewModel.changeUiStateLiveData.observe(viewLifecycleOwner) {
        if (listenWhen(it.first, it.second)) {
            block(it.second)
        }
    }
}

fun <E, VM> ViewFragment.Stateful<*, VM>.onUiEvent(
    block: (uiEvent: E) -> Unit
) where VM: ViewModel, VM: UiEventHolder<E> {
    viewLifecycleOwner.lifecycleScope.launch {
        viewModel.setOnUiEvent {
            block(it)
        }
    }
}

fun <VM> ViewFragment.Stateful<*, VM>.handleDefaultError(
    block: (defaultError: Defaults<Nothing>) -> Unit
) where VM: StatelessViewModel<*, *> {
    viewLifecycleOwner.lifecycleScope.launch {
        viewModel.setOnDefaultError {
            block(it)
        }
    }
}

fun <VM> ViewFragment.Stateful<*, VM>.handleLoading() where VM: ViewModel, VM: ViewModelLoadable {
    handleLoading(viewModel)
}

fun <VM, I> ViewFragment.Stateful<*, VM>.execute(intent: I) where VM: StatefulViewModel<*, I, *>, VM: ViewModelLoadable {
    viewModel.executeUiIntent(intent)
}

fun <E, VM> ViewFragment.StatefulDataBinding<*, VM>.onUiState(
listenWhen: (lastUiState: E?, newUiState: E) -> Boolean = { _, _ -> true },
block: (uiState: E) -> Unit
) where VM: ViewModel, VM: UiStateHolder<E> {
    viewModel.changeUiStateLiveData.observe(viewLifecycleOwner) {
        if (listenWhen(it.first, it.second)) {
            block(it.second)
        }
    }
}

fun <E, VM> ViewFragment.StatefulDataBinding<*, VM>.onUiEvent(
    block: (uiEvent: E) -> Unit
) where VM: ViewModel, VM: UiEventHolder<E> {
    viewLifecycleOwner.lifecycleScope.launch {
        viewModel.setOnUiEvent {
            block(it)
        }
    }
}

fun <VM> ViewFragment.StatefulDataBinding<*, VM>.handleDefaultError(
    block: (defaultError: Defaults<Nothing>) -> Unit
) where VM: StatelessViewModel<*, *> {
    viewLifecycleOwner.lifecycleScope.launch {
        viewModel.setOnDefaultError {
            block(it)
        }
    }
}

fun <VM> ViewFragment.StatefulDataBinding<*, VM>.handleLoading() where VM: ViewModel, VM: ViewModelLoadable {
    handleLoading(viewModel)
}

fun <VM, I> ViewFragment.StatefulDataBinding<*, VM>.execute(intent: I) where VM: StatefulViewModel<*, I, *>, VM: ViewModelLoadable {
    viewModel.executeUiIntent(intent)
}