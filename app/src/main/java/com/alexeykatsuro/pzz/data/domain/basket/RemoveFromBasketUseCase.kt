package com.alexeykatsuro.pzz.data.domain.basket

import com.alexeykatsuro.pzz.data.AppCoroutineDispatchers
import com.alexeykatsuro.pzz.data.domain.UseCase
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.repository.basket.BasketRepository
import javax.inject.Inject

class RemoveFromBasketUseCase @Inject constructor(
    dispatchers: AppCoroutineDispatchers,
    private val basketRepository: BasketRepository
) : UseCase<RemoveFromBasketUseCase.Params, Basket>() {

    override val dispatcher = dispatchers.io

    override suspend fun doWork(params: Params): Basket =
        basketRepository.removeItem(
            id = params.id,
            size = params.size,
            type = params.type
        )

    operator fun invoke(id: Long, type: String, size: String) = invoke(Params(id, type, size))

    data class Params(val id: Long, val type: String, val size: String)
}