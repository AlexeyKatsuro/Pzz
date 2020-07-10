package com.alexeykatsuro.pzz.data.entities

import com.alexeykatsuro.pzz.utils.StorableEnum

enum class ProductType(override val value: String) : StorableEnum<String> {
    Pizza("pizza"),
    Sauce("sauce"),
    Unknown("Unknown"),
}