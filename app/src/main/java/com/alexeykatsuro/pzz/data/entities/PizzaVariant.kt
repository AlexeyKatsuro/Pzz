package com.alexeykatsuro.pzz.data.entities

import com.alexeykatsuro.pzz.utils.StorableEnum

typealias PizzaVariants = List<PizzaVariant>

class PizzaVariant(
    val size: Size,
    val price: Float,
    val weight: String,
    val diameter: String
) {
    enum class Size(override val value: String): StorableEnum<String> {
        Big("big"),
        Medium("medium"),
        Thin("thin"),
        Unknown("Unknown");
    }
}

fun PizzaVariants.hasSize(size: PizzaVariant.Size) =
    any { it.size == size }

operator fun PizzaVariants.get(size: PizzaVariant.Size): PizzaVariant? = find {
    it.size == size
}

inline val PizzaVariants.hasBig: Boolean get() = hasSize(PizzaVariant.Size.Big)
inline val PizzaVariants.hasMedium: Boolean get() = hasSize(PizzaVariant.Size.Medium)
inline val PizzaVariants.hasThin: Boolean get() = hasSize(PizzaVariant.Size.Thin)