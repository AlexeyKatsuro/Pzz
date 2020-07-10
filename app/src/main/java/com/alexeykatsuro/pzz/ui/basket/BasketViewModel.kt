package com.alexeykatsuro.pzz.ui.basket

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.alexeykatsuro.pzz.base.PzzViewModel
import com.alexeykatsuro.pzz.data.domain.basket.AddToBasketUseCase
import com.alexeykatsuro.pzz.data.domain.basket.RemoveFromBasketUseCase
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.data.entities.basket.getSize
import com.alexeykatsuro.pzz.utils.GlobalBasket
import com.alexeykatsuro.pzz.utils.extensions.trackError
import com.alexeykatsuro.pzz.utils.extensions.trackLoading
import com.alexeykatsuro.pzz.utils.extensions.trackSuccess
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber

class BasketViewModel @AssistedInject constructor(
    @Assisted initialState: BasketViewState,
    //observeBasketUseCase: ObserveBasketUseCase,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val removeFromBasketUseCase: RemoveFromBasketUseCase,
    private val navigation: BasketNavigation
) : PzzViewModel<BasketViewState>(initialState) {

    init {
        /* viewModelScope.launchObserve(observeBasketUseCase){ flow ->
             flow.distinctUntilChanged().execute {
                 copy(basket = it)
             }
         }
         observeBasketUseCase()*/
        viewModelScope.launch {
            GlobalBasket.observeBasket().distinctUntilChanged().execute {
                copy(basket = it)
            }
        }
    }


    fun onRemoveClick(product: Product) {
        val params = RemoveFromBasketUseCase.Params(
            id = product.id,
            type = product.type.value,
            size = product.getSize()
        )
        removeFromBasketUseCase(params)
            .trackError(errorEventBus)
            .trackLoading { setState { copy(isLoading = it) } }
            .trackSuccess {
                Timber.e("$it")
            }.launch()
    }

    fun onAddClick(product: Product) {
        val params = AddToBasketUseCase.Params(
            id = product.id,
            type = product.type.value,
            size = product.getSize()
        )
        addToBasketUseCase(params)
            .trackError(errorEventBus)
            .trackLoading { setState { copy(isLoading = it) } }
            .trackSuccess {
                Timber.e("$it")
            }.launch()
    }

    @AssistedInject.Factory
    interface Factory {
        fun create(initialState: BasketViewState): BasketViewModel
    }

    companion object : MvRxViewModelFactory<BasketViewModel, BasketViewState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: BasketViewState
        ): BasketViewModel? {
            val fragment: BasketFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.assistedViewModelFactory.create(state)
        }
    }
}