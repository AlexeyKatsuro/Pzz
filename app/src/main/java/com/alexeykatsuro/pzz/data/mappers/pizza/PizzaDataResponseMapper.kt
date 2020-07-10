package com.alexeykatsuro.pzz.data.mappers.pizza

import com.alexeykatsuro.pzz.data.dto.PizzaDataResponse
import com.alexeykatsuro.pzz.data.entities.Pizza
import com.alexeykatsuro.pzz.data.entities.PizzaVariant
import com.alexeykatsuro.pzz.data.mappers.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PizzaDataResponseMapper @Inject constructor() :
    Mapper<PizzaDataResponse, Pizza> {
    override fun map(from: PizzaDataResponse): Pizza {
        return Pizza(
            id = requireNotNull(from.id),
            name = from.title.orEmpty(),
            description = from.anonce.orEmpty(),
            thumbnail = from.photoSmall.orEmpty(),
            photo = from.photo1.orEmpty(),
            variants = mapVariants(from)
        )
    }

    private fun mapVariants(from: PizzaDataResponse): List<PizzaVariant> {
        return mutableListOf<PizzaVariant>().apply {
            if (from.isBig == 1) add(mapBigVariants(from))
            if (from.isMedium == 1) add(mapMediumVariants(from))
            if (from.isThin == 1) add(mapThinVariants(from))
        }
    }

    private fun mapBigVariants(from: PizzaDataResponse) =
        PizzaVariant(
            size = PizzaVariant.Size.Big,
            weight = from.bigWeight.orEmpty(),
            diameter = from.bigDiameter.orEmpty(),
            price = (from.bigPrice ?: 0) / 10000f
        )

    private fun mapMediumVariants(from: PizzaDataResponse) =
        PizzaVariant(
            size = PizzaVariant.Size.Medium,
            weight = from.mediumWeight.orEmpty(),
            diameter = from.mediumDiameter.orEmpty(),
            price = (from.mediumPrice ?: 0) / 10000f
        )

    private fun mapThinVariants(from: PizzaDataResponse) =
        PizzaVariant(
            size = PizzaVariant.Size.Thin,
            weight = from.thinWeight.orEmpty(),
            diameter = from.thinDiameter.orEmpty(),
            price = (from.thinPrice ?: 0) / 10000f
        )
}