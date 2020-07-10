package com.alexeykatsuro.pzz.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.alexeykatsuro.pzz.R
import com.alexeykatsuro.pzz.base.BindingInflater
import com.alexeykatsuro.pzz.base.PzzFragment
import com.alexeykatsuro.pzz.databinding.FragmentHomeBinding
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.tabs.TabLayout
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("RestrictedApi")
class HomeFragment : PzzFragment<FragmentHomeBinding>() {
    @Inject
    lateinit var assistedViewModelFactory: HomeViewModel.Factory
    override val viewModel: HomeViewModel by fragmentViewModel()

    @Inject
    lateinit var controller: HomeController

    override val bindingInflater: BindingInflater<FragmentHomeBinding> =
        FragmentHomeBinding::inflate


    override fun onViewCreated(binding: FragmentHomeBinding, savedInstanceState: Bundle?) {
        withBinding {
            withState(viewModel) {
                state = it
            }

            onMenu = Toolbar.OnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_basket -> {
                        viewModel.onBasketMenuClick()
                        true
                    }
                    else -> false
                }
            }
            controller.callbacks = controllerCallbacks
            homeRv.setController(controller)
        }
        val badgeDrawable  = BadgeDrawable.create(requireContext()).apply {
            number = 5
            backgroundColor = Color.CYAN
        }
        BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.fab, binding.frameLayout);
        viewModel.asyncSubscribe(viewLifecycleOwner, HomeViewState::basket) {
            it.products.forEach {
                Timber.e("$it")
            }


        }

    }

    override fun invalidate() = withState(viewModel) { state ->
        requireBinding().state = state
        controller.setData(state)
    }

    private val controllerCallbacks = object : HomeController.Callbacks {
        override fun onPizzaToBasketClick(pizzaTyped: PizzaTyped) {
            viewModel.onPizzaToBasketClick(pizzaTyped)
        }

        override fun onPizzaHolderChangedClick(pizzaTyped: PizzaTyped) {
            viewModel.onPizzaHolderChangedClick(pizzaTyped)
        }
    }
}