package com.alexeykatsuro.pzz.ui.home

import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.FragmentViewModelContext
import com.airbnb.mvrx.MvRxViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.alexeykatsuro.pzz.base.PzzViewModel
import com.alexeykatsuro.pzz.data.domain.basket.AddToBasketUseCase
import com.alexeykatsuro.pzz.data.domain.basket.ObserveBasketUseCase
import com.alexeykatsuro.pzz.data.domain.invoke
import com.alexeykatsuro.pzz.data.domain.launchObserve
import com.alexeykatsuro.pzz.data.domain.pizza.LoadPizzasUseCase
import com.alexeykatsuro.pzz.data.entities.ProductType
import com.alexeykatsuro.pzz.utils.GlobalBasket
import com.alexeykatsuro.pzz.utils.extensions.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel @AssistedInject constructor(
    @Assisted initialState: HomeViewState,
    loadPizzasUseCase: LoadPizzasUseCase,
   // observeBasketUseCase: ObserveBasketUseCase,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val navigation: HomeNavigation
) : PzzViewModel<HomeViewState>(initialState) {

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

        loadPizzasUseCase()
            .trackError(errorEventBus)
            .trackLoading { setState { copy(isLoading = it) } }
            .trackSuccess {
                val holders = it.map { pizza ->
                    PizzaTyped(
                        pizza = pizza,
                        selectedSize = pizza.variants.first().size
                    )
                }
                setState { copy(pizzaTypeds = holders) }
            }.launch()
    }

    fun onPizzaHolderChangedClick(pizzaTyped: PizzaTyped) = setState {
        copy(pizzaTypeds = pizzaTypeds.upsert(pizzaTyped){
            it.id == pizzaTyped.id
        })
    }

    fun onBasketMenuClick() {
        navigateEvent.postEvent(navigation.toBasket)
    }


    fun onPizzaToBasketClick(pizzaTyped: PizzaTyped) {
        val params =AddToBasketUseCase.Params(
            id = pizzaTyped.id,
            type = ProductType.Pizza.value,
            size = pizzaTyped.selectedSize.value
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
        fun create(initialState: HomeViewState): HomeViewModel
    }

    companion object : MvRxViewModelFactory<HomeViewModel, HomeViewState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeViewState
        ): HomeViewModel? {
            val fragment: HomeFragment = (viewModelContext as FragmentViewModelContext).fragment()
            return fragment.assistedViewModelFactory.create(state)
        }
    }
}