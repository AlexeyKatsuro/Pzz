package com.alexeykatsuro.pzz.data.repository.basket

import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.getOrThrow
import com.alexeykatsuro.pzz.utils.GlobalBasket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketRepository @Inject constructor(
    private val basketStore: BasketStore,
    private val dataSource: BasketDataSource
) {

    suspend fun addItem(type: String, id: Long, size: String): Basket {
        val basket = dataSource.addItem(id, type, size).getOrThrow()
        //basketStore.updateBasket(basket)
        GlobalBasket.updateBasket(basket) // TODO remove Singleton crutch
        return basket
    }

    suspend fun removeItem(type: String, id: Long, size: String): Basket {
        val basket = dataSource.removeItem(id, type, size).getOrThrow()
        //basketStore.updateBasket(basket)
        GlobalBasket.updateBasket(basket) // TODO remove Singleton crutch
        return basket
    }

    fun observeBasket() = basketStore.observeBasket()
}