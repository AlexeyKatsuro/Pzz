package com.alexeykatsuro.pzz.ui.basket

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.alexeykatsuro.pzz.data.entities.ProductType
import com.alexeykatsuro.pzz.data.entities.basket.Product
import com.alexeykatsuro.pzz.data.entities.basket.PizzaProduct
import com.alexeykatsuro.pzz.di.PerActivity
import com.alexeykatsuro.pzz.itemProduct
import com.alexeykatsuro.pzz.utils.toLocalizedString
import timber.log.Timber
import javax.inject.Inject

class BasketController @Inject constructor(
    @PerActivity val context: Context
) : TypedEpoxyController<BasketViewState>() {

    var callbacks: Callbacks? = null

    interface Callbacks {
        // Add own callbacks methods
        fun onAddClick(product: Product)
        fun onRemoveClick(product: Product)

    }

    override fun buildModels(data: BasketViewState) {
        data.productsCounted.forEachIndexed { index, countWrapper ->
            Timber.e("forEachIndexed ${countWrapper.product}")
            itemProduct {
                id("product_$index")
                item(countWrapper.product)
                size(getProductSize(countWrapper.product))
                count(countWrapper.count)
                onCountMinus { model, _, _, _ ->
                    callbacks?.onRemoveClick(model.item())
                }
                onCountPlus { model, _, _, _ ->
                    callbacks?.onAddClick(model.item())
                }
            }
        }
    }

    private fun getProductSize(product: Product): String {
        return when (product.type) {
            ProductType.Pizza -> (product as PizzaProduct).size.toLocalizedString(context)
            else -> ""
        }
    }
}