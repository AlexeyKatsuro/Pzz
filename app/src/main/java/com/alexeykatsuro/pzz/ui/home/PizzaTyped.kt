package com.alexeykatsuro.pzz.ui.home

import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.entities.PizzaVariant
import com.alexeykatsuro.pzz.data.entities.PizzaVariants

data class PizzaTyped(
    val pizza: Pizza,
    val selectedSize: PizzaVariant.Size
)

val PizzaTyped.id: Long get() = pizza.id
val PizzaTyped.name: String get() = pizza.name
val PizzaTyped.description: String get() = pizza.description
val PizzaTyped.thumbnail: String get() = pizza.thumbnail
val PizzaTyped.photo: String get() = pizza.photo
val PizzaTyped.variants: PizzaVariants get() = pizza.variants
