package com.alexeykatsuro.pzz.ui.home

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.alexeykatsuro.pzz.data.entities.basket.Basket

data class HomeViewState(
    val pizzaTypeds: List<PizzaTyped> = emptyList(),
    val isLoading: Boolean = false,
    val basket: Async<Basket> = Uninitialized
) : MvRxState