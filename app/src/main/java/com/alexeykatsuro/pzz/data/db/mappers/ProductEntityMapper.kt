package com.alexeykatsuro.pzz.data.db.mappers

import com.alexeykatsuro.pzz.data.db.entries.ProductEntry
import com.alexeykatsuro.pzz.data.db.mappers.product.PizzaProductEntryMapper
import com.alexeykatsuro.pzz.data.db.mappers.product.SauceProductEntryMapper
import com.alexeykatsuro.pzz.data.db.mappers.product.UnknownProductEntryMapper
import com.alexeykatsuro.pzz.data.entities.ProductType
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.data.entities.basket.PizzaProduct
import com.alexeykatsuro.pzz.data.entities.basket.SauceBasket
import com.alexeykatsuro.pzz.data.entities.basket.UnknownProduct
import com.alexeykatsuro.pzz.utils.toEnum
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductEntityMapper @Inject constructor(
    private val pizzaProductEntryMapper: PizzaProductEntryMapper,
    private val sauceProductEntryMapper: SauceProductEntryMapper,
    private val unknownProductEntryMapper: UnknownProductEntryMapper
) :
    EntityMapper<ProductEntry, Product> {
    override fun fromEntity(from: ProductEntry): Product {
        return when (toEnum<ProductType, String>(from.type)) {
            ProductType.Pizza -> pizzaProductEntryMapper.fromEntity(from)
            ProductType.Sauce -> sauceProductEntryMapper.fromEntity(from)
            ProductType.Unknown -> unknownProductEntryMapper.fromEntity(from)
        }
    }

    override fun toEntity(from: Product): ProductEntry {
        return when (from.type) {
            ProductType.Pizza -> pizzaProductEntryMapper.toEntity(from as PizzaProduct)
            ProductType.Sauce -> sauceProductEntryMapper.toEntity(from as SauceBasket)
            ProductType.Unknown -> unknownProductEntryMapper.toEntity(from as UnknownProduct)
        }
    }
}