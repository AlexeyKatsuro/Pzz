package com.alexeykatsuro.pzz.ui.basket

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.utils.CountWrapper

data class BasketViewState(
    val basket: Async<Basket> = Uninitialized,
    val isLoading: Boolean = false
) : MvRxState {

    val productsCounted: List<CountWrapper<Product>> = basket()?.products.orEmpty().groupBy {
        it
    }.map {
        CountWrapper(product = it.key, count = it.value.size)
    }
}