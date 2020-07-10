package com.alexeykatsuro.pzz.data.mappers.pizza

import com.alexeykatsuro.pzz.data.dto.PizzaResponse
import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.mappers.Mapper
import com.alexeykatsuro.pzz.data.mappers.toListSafeMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaResponseMapper @Inject constructor(
    private val pizzaDataResponseMapper: PizzaDataResponseMapper
) : Mapper<PizzaResponse, List<Pizza>> {
    private val pizzaDataResponseListMapper = pizzaDataResponseMapper.toListSafeMapper()

    override fun map(from: PizzaResponse): List<Pizza> {
        val data = requireNotNull(from.response?.data) { "Required response.data was null" }
        return pizzaDataResponseListMapper.map(data)
    }
}