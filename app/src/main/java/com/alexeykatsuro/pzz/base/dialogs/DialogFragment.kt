package com.alexeykatsuro.pzz.base.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.alexeykatsuro.pzz.base.BindingInflater

abstract class DialogFragment<V : ViewDataBinding> : DaggerMvRxDialogFragment() {


    private var binding: V? = null
    abstract val bindingInflater: BindingInflater<V>


    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return createBinding(inflater, container, savedInstanceState).also {
            it.lifecycleOwner = viewLifecycleOwner
            binding = it
        }.root
    }

    protected fun requireBinding(): V = requireNotNull(binding)
    protected inline fun withBinding(block: V.() -> Unit) = requireBinding().block()

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated(requireBinding(), savedInstanceState)
    }

    protected open fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): V = bindingInflater(inflater, container, false)

    abstract fun onViewCreated(binding: V, savedInstanceState: Bundle?)


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}