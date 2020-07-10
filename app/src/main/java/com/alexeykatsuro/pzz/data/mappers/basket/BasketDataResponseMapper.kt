package com.alexeykatsuro.pzz.data.mappers.basket

import com.alexeykatsuro.pzz.data.dto.basket.BasketDataResponse
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.mappers.Mapper
import com.alexeykatsuro.pzz.data.mappers.toListSafeMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketDataResponseMapper @Inject constructor(
    private val basketProductResponseMapper: BasketProductResponseMapper
) : Mapper<BasketDataResponse, Basket> {
    private val basketProductResponseListMapper = basketProductResponseMapper.toListSafeMapper()
    override fun map(from: BasketDataResponse): Basket {
        return Basket(basketProductResponseListMapper.map(from.items.orEmpty()))
    }
}