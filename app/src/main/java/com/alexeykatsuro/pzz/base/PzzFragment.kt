package com.alexeykatsuro.pzz.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BuildConfig
import com.airbnb.mvrx.MvRxState
import com.alexeykatsuro.pzz.data.ErrorType
import com.alexeykatsuro.pzz.data.ProcessLifetime
import com.alexeykatsuro.pzz.data.ThrowableResult
import com.alexeykatsuro.pzz.utils.*
import com.alexeykatsuro.pzz.utils.extensions.longToast
import com.alexeykatsuro.pzz.utils.extensions.navigateGlobal
import com.alexeykatsuro.pzz.utils.extensions.restart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import javax.inject.Inject

typealias BindingInflater<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class PzzFragment<V : ViewDataBinding> : DaggerMvRxFragment() {


    private var binding: V? = null
    private lateinit var navController: NavController
    protected abstract val viewModel: PzzViewModel<out MvRxState>

    abstract val bindingInflater: BindingInflater<V>

    @ProcessLifetime
    @Inject
    lateinit var processScope: CoroutineScope

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
        navController = findNavController()
        onViewCreated(requireBinding(), savedInstanceState)

        viewModel.onNavigateEvent.observeEvent(viewLifecycleOwner) {
            handleNavigation(it)
        }
        viewModel.onErrorEvent.observeEvent(viewLifecycleOwner) {
            handleError(it)
        }
        viewModel.onMessageResEvent.observeEvent(viewLifecycleOwner) {
            handleMessageRes(it)
        }
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


    open fun handleError(throwable: Throwable) {
        if (throwable is ThrowableResult) {

            longToast(throwable.message ?: throwable.type.toLocalizedString(requireContext()))

            if (throwable.type == ErrorType.Unauthorized) {
                requireActivity().restart()
            }
        } else if (BuildConfig.DEBUG) {
            longToast(throwable.message ?: return)
        }
    }

    open fun handleNavigation(navEvent: BaseNavEvent): Unit = when (navEvent) {
        NavigateUp -> {
            navController.navigateUp().let { Unit }
        }
        StartOver -> {
            // Cancel all job for processScope
            processScope.coroutineContext.cancelChildren()
            requireActivity().restart()
        }
        is PopBackStake -> {
            navController.popBackStack(
                navEvent.destinationId,
                navEvent.inclusive
            ).let { Unit }
        }
        is NavCommand -> {
            navController.navigate(navEvent.action, navEvent.args, navEvent.navOptions)
        }
        is GlobalNavCommand -> {
            navigateGlobal(navEvent)
        }
    }

    open fun handleMessageRes(stringRes: Int) {
        longToast(stringRes)
    }
}