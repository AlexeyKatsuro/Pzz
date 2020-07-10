package com.alexeykatsuro.pzz.ui.basket

import com.alexeykatsuro.pzz.R
import com.alexeykatsuro.pzz.databinding.FragmentBasketBinding
import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.alexeykatsuro.pzz.ItemProductBindingModel_
import com.alexeykatsuro.pzz.base.BindingInflater
import com.alexeykatsuro.pzz.base.PzzFragment
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.utils.decorators.DividerModelDecoration
import com.alexeykatsuro.pzz.utils.epoxy.PaddingView
import timber.log.Timber

import javax.inject.Inject

class BasketFragment : PzzFragment<FragmentBasketBinding>() {
    @Inject
    lateinit var assistedViewModelFactory: BasketViewModel.Factory

    override val viewModel: BasketViewModel by fragmentViewModel()

    @Inject
    lateinit var controller: BasketController

    override val bindingInflater: BindingInflater<FragmentBasketBinding> =
        FragmentBasketBinding::inflate

    override fun onViewCreated(binding: FragmentBasketBinding, savedInstanceState: Bundle?) {
        withBinding {
            withState(viewModel) {
                state = it
            }
            controller.callbacks = controllerCallbacks
            val divider = DividerModelDecoration(
                context = requireContext(),
                controller = controller,
                padding = PaddingView.resource(leftRes = R.dimen.activity_horizontal_space),
                filterModels = {
                    it is ItemProductBindingModel_
                }
            )
            basketRv.addItemDecoration(divider)
            basketRv.setController(controller)
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        Timber.e("${state.productsCounted}")
        requireBinding().state = state
        controller.setData(state)
    }

    private val controllerCallbacks = object : BasketController.Callbacks {
        override fun onAddClick(product: Product) {
            viewModel.onAddClick(product)
        }

        override fun onRemoveClick(product: Product) {
            viewModel.onRemoveClick(product)
        }
    }
}