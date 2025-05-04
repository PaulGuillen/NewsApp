package com.devpaul.core_platform.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.devpaul.core_platform.fragment.base.AppFragment


sealed class ViewFragment : AppFragment() {

    /** ViewBinding */
    abstract class Stateless<V : ViewBinding> : AppFragment() {
        val binding by viewBinding { inflater ->
            getViewBinding(inflater)
        }

        abstract fun getViewBinding(inflater: LayoutInflater): V

        open fun onInit() {}

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ) = binding.root

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            onInit()
        }
    }

    /** ViewBinding + ViewModel */
    abstract class Stateful<V : ViewBinding, VM : ViewModel> : Stateless<V>() {

        val viewModel: VM by lazy { getViewModel().value }

        abstract fun getViewModel(): Lazy<VM>
    }

    /** DataBinding + ViewBinding + ViewModel */
    abstract class StatefulDataBinding<V : ViewDataBinding, VM : ViewModel> : ViewFragment() {
        val binding by dataBinding { inflater, container, _ ->
            getDataBinding(inflater, container)
        }

        val viewModel: VM by lazy { getViewModel().value }

        abstract fun getViewModel(): Lazy<VM>
        abstract fun getDataBinding(inflater: LayoutInflater, container: ViewGroup?): V

        open fun onInit() {}

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding.lifecycleOwner = viewLifecycleOwner
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            onInit()
        }
    }
}