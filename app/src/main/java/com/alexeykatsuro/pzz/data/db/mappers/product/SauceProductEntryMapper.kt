package com.alexeykatsuro.pzz.data.db.mappers.product

import com.alexeykatsuro.pzz.data.db.entries.ProductEntry
import com.alexeykatsuro.pzz.data.db.mappers.EntityMapper
import com.alexeykatsuro.pzz.data.entities.basket.SauceBasket
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SauceProductEntryMapper @Inject constructor() :
    EntityMapper<ProductEntry, SauceBasket> {
    override fun toEntity(from: SauceBasket): ProductEntry {
        return ProductEntry(
            localId = 0,
            serverId = from.id,
            title = from.title,
            price = from.price,
            type = from.type.value
        )
    }

    override fun fromEntity(from: ProductEntry): SauceBasket {
        return SauceBasket(
            id = from.serverId,
            title = from.title,
            price = from.price
        )
    }
}