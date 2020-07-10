package com.alexeykatsuro.pzz.data.mappers.basket

import com.alexeykatsuro.pzz.data.dto.basket.BasketResponse
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.mappers.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketResponseMapper @Inject constructor(
    private val basketDataResponseMapper: BasketDataResponseMapper
) : Mapper<BasketResponse, Basket> {

    override fun map(from: BasketResponse): Basket {
        val data = requireNotNull(from.response?.data) { "Required response.data was null" }
        return basketDataResponseMapper.map(data)
    }
}