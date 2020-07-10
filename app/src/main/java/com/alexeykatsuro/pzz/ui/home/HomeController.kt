package com.alexeykatsuro.pzz.ui.home

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.alexeykatsuro.pzz.di.PerActivity
import com.alexeykatsuro.pzz.ui.widgets.PizzaView
import com.alexeykatsuro.pzz.ui.widgets.pizzaView
import javax.inject.Inject

class HomeController @Inject constructor(
    @PerActivity val context: Context
) : TypedEpoxyController<HomeViewState>() {

    var callbacks: Callbacks? = null

    interface Callbacks {
        // Add own callbacks methods
        fun onPizzaToBasketClick(pizzaTyped: PizzaTyped)
        fun onPizzaHolderChangedClick(pizzaTyped: PizzaTyped)
    }

    override fun buildModels(data: HomeViewState) {
        data.pizzaTypeds.forEach { pizzaHolder ->
            pizzaView {
                id("pizza", pizzaHolder.id)
                pizza(pizzaHolder)
                thumbnail(pizzaHolder.photo)
                onPizzaVariantTypeListener(PizzaView.OnPizzaVariantTypeListener { pizzaHolder, type ->
                    callbacks?.onPizzaHolderChangedClick(pizzaHolder.copy(selectedSize = type))
                })
                onToBasketClick { model, _, _, _ ->
                    callbacks?.onPizzaToBasketClick(model.pizza())
                }
            }
        }
    }
}