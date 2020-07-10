package com.alexeykatsuro.pzz.data.db.mappers.product

import com.alexeykatsuro.pzz.data.db.entries.ProductEntry
import com.alexeykatsuro.pzz.data.db.mappers.EntityMapper
import com.alexeykatsuro.pzz.data.entities.basket.UnknownProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnknownProductEntryMapper @Inject constructor() :
    EntityMapper<ProductEntry, UnknownProduct> {
    override fun toEntity(from: UnknownProduct): ProductEntry {
        return ProductEntry(
            localId = 0,
            serverId = from.id,
            title = from.title,
            price = from.price,
            type = from.type.value
        )
    }

    override fun fromEntity(from: ProductEntry): UnknownProduct {
        return UnknownProduct(
            id = from.serverId,
            title = from.title,
            price = from.price
        )
    }
}