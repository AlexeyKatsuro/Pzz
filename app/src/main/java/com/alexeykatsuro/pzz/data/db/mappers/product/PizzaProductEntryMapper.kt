package com.alexeykatsuro.pzz.data.db.mappers.product

import com.alexeykatsuro.pzz.data.db.entries.ProductEntry
import com.alexeykatsuro.pzz.data.db.mappers.EntityMapper
import com.alexeykatsuro.pzz.data.entities.basket.PizzaProduct
import com.alexeykatsuro.pzz.utils.toEnum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaProductEntryMapper @Inject constructor() :
    EntityMapper<ProductEntry, PizzaProduct> {
    override fun toEntity(from: PizzaProduct): ProductEntry {
        return ProductEntry(
            localId = 0,
            serverId = from.id,
            title = from.title,
            price = from.price,
            type = from.type.value,
            size = from.size.value
        )
    }

    override fun fromEntity(from: ProductEntry): PizzaProduct {
        return PizzaProduct(
            id = from.serverId,
            title = from.title,
            price = from.price,
            size = toEnum(from.size)
        )
    }
}