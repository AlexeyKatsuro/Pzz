package com.alexeykatsuro.pzz.data.repository.basket

import com.alexeykatsuro.pzz.data.Result
import com.alexeykatsuro.pzz.data.RetrofitRunner
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.mappers.basket.BasketResponseMapper
import com.alexeykatsuro.pzz.data.network.BasketService
import javax.inject.Inject
import javax.inject.Provider

class BasketDataSource @Inject constructor(
    private val service: Provider<BasketService>,
    private val basketResponseMapper: BasketResponseMapper,
    private val retrofitRunner: RetrofitRunner
) {
    suspend fun addItem(id: Long, type: String, size: String): Result<Basket> {
        return retrofitRunner.invoke(basketResponseMapper) {
            service.get().addItem(id, type, size)
        }
    }

    suspend fun removeItem(id: Long, type: String, size: String): Result<Basket> {
        return retrofitRunner.invoke(basketResponseMapper) {
            service.get().removeItem(id, type, size)
        }
    }
}