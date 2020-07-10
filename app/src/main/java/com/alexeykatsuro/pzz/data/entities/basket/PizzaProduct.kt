package com.alexeykatsuro.pzz.data.entities.basket

import com.alexeykatsuro.pzz.data.entities.PizzaVariant
import com.alexeykatsuro.pzz.data.entities.ProductType

data class PizzaProduct(
    override val id: Long,
    override val title: String,
    override val price: Float,
    val size: PizzaVariant.Size
) : Product {
    override val type = ProductType.Pizza
}
