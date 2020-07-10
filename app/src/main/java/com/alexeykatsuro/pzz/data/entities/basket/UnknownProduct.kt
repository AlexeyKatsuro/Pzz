package com.alexeykatsuro.pzz.data.entities.basket

import com.alexeykatsuro.pzz.data.entities.ProductType

data class UnknownProduct(
    override val id: Long,
    override val title: String,
    override val price: Float
): Product {
    override val type = ProductType.Unknown
}