package com.alexeykatsuro.pzz.data.repository

import com.alexeykatsuro.pzz.data.Result
import com.alexeykatsuro.pzz.data.RetrofitRunner
import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.mappers.pizza.PizzaResponseMapper
import com.alexeykatsuro.pzz.data.network.PizzaService
import javax.inject.Inject
import javax.inject.Provider

class PizzaDataSource @Inject constructor(
    private val service: Provider<PizzaService>,
    private val pizzaResponseMapper: PizzaResponseMapper,
    private val retrofitRunner: RetrofitRunner
) {

    suspend fun loadPizzas(): Result<List<Pizza>> {
        return retrofitRunner.invoke(pizzaResponseMapper) {
            service.get().getPizza()
        }
    }
}