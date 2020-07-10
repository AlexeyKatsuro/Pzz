package com.alexeykatsuro.pzz.data.domain.basket

import com.alexeykatsuro.pzz.data.AppCoroutineDispatchers
import com.alexeykatsuro.pzz.data.domain.SubjectUseCase
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import com.alexeykatsuro.pzz.data.repository.basket.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveBasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository,
    dispatchers: AppCoroutineDispatchers
) : SubjectUseCase<Unit, Basket>() {
    override val dispatcher = dispatchers.io

    override fun createObservable(params: Unit): Flow<Basket> =
        basketRepository.observeBasket()
}