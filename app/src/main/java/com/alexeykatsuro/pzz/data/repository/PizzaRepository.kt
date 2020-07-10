package com.alexeykatsuro.pzz.data.repository

import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.getOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaRepository @Inject constructor(
    private val pizzaDataSource: PizzaDataSource
) {

    suspend fun loadPizzas(): List<Pizza> =
        pizzaDataSource.loadPizzas().getOrThrow()
}