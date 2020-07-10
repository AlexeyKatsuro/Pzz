package com.alexeykatsuro.pzz.data.domain.pizza

import com.alexeykatsuro.pzz.data.AppCoroutineDispatchers
import com.alexeykatsuro.pzz.data.domain.UseCase
import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.repository.PizzaRepository
import javax.inject.Inject

class LoadPizzasUseCase @Inject constructor(
    dispatchers: AppCoroutineDispatchers,
    private val pizzaRepository: PizzaRepository
) : UseCase<Unit, List<Pizza>>() {

    override val dispatcher = dispatchers.io

    override suspend fun doWork(params: Unit): List<Pizza> =
        pizzaRepository.loadPizzas()
}