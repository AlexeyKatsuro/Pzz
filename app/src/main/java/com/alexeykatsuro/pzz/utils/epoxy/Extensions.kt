package com.alexeykatsuro.pzz.utils.epoxy

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel


/** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
 *
 * @param items The items to transform to models
 * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item.
 */
inline fun <T> CarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (T) -> EpoxyModel<*>
) {
    models(items.map { modelBuilder(it) })
}

inline fun <T> CarouselModelBuilder.withModelsFrom(
    items: List<T>,
    modelBuilder: (index: Int, item: T) -> EpoxyModel<*>
) {
    models(items.mapIndexed { index, item -> modelBuilder(index, item) })
}

fun RecyclerView.syncSpanSizes(controller: EpoxyController) {
    val layout = layoutManager
    if (layout is GridLayoutManager) {
        if (controller.spanCount != layout.spanCount ||
            layout.spanSizeLookup !== controller.spanSizeLookup
        ) {
            controller.spanCount = layout.spanCount
            layout.spanSizeLookup = controller.spanSizeLookup
        }
    }
}