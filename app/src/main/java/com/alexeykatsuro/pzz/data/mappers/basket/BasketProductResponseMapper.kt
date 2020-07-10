package com.alexeykatsuro.pzz.data.mappers.basket

import com.alexeykatsuro.pzz.data.dto.basket.BasketDataResponse
import com.alexeykatsuro.pzz.data.entities.PizzaVariant
import com.alexeykatsuro.pzz.data.entities.ProductType
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.data.entities.basket.PizzaProduct
import com.alexeykatsuro.pzz.data.entities.basket.SauceBasket
import com.alexeykatsuro.pzz.data.entities.basket.UnknownProduct
import com.alexeykatsuro.pzz.data.mappers.Mapper
import com.alexeykatsuro.pzz.utils.toEnumOrDefault
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasketProductResponseMapper @Inject constructor() :
    Mapper<BasketDataResponse.Products, Product> {
    override fun map(from: BasketDataResponse.Products): Product {
        return when (toEnumOrDefault(from.type, ProductType.Unknown)) {
            ProductType.Pizza -> mapPizzaProduct(from)
            ProductType.Sauce -> mapSauceProduct(from)
            ProductType.Unknown -> mapUnknownProduct(from)
        }
    }

    private fun mapPizzaProduct(from: BasketDataResponse.Products) =
        PizzaProduct(
            id = requireNotNull(from.id),
            title = from.title.orEmpty(),
            price = (from.price ?: 0) / 10000f,
            size = toEnumOrDefault(from.size, PizzaVariant.Size.Unknown)
        )

    private fun mapSauceProduct(from: BasketDataResponse.Products) =
        SauceBasket(
            id = requireNotNull(from.id),
            title = from.title.orEmpty(),
            price = (from.price ?: 0) / 10000f
        )

    private fun mapUnknownProduct(from: BasketDataResponse.Products) =
        UnknownProduct(
            id = requireNotNull(from.id),
            title = from.title.orEmpty(),
            price = (from.price ?: 0) / 10000f
        )
}