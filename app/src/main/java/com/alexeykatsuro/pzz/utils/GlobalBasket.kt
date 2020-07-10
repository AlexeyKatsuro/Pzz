package com.alexeykatsuro.pzz.utils

import com.alexeykatsuro.pzz.data.entities.basket.Basket
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.asFlow

object GlobalBasket {

    private val basketChannel = ConflatedBroadcastChannel<Basket>()

    fun observeBasket() = basketChannel.asFlow()

    suspend fun updateBasket(basket: Basket) =
        basketChannel.send(basket)
}