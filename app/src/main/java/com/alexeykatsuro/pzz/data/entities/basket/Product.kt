package com.alexeykatsuro.pzz.data.entities.basket

import com.alexeykatsuro.pzz.data.entities.ProductType

interface Product {
    val id: Long
    val title: String
    val price: Float
    val type: ProductType
}

fun Product.getSize() = when (type) {
    ProductType.Pizza -> {
        (this as PizzaProduct).run {
            size.value
        }
    }

    else -> ""
}