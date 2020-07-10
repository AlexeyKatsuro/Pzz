@file:JvmName("EnumUtils")

package com.alexeykatsuro.pzz.utils

import android.content.Context
import com.alexeykatsuro.pzz.R
import com.alexeykatsuro.pzz.data.ErrorType
import com.alexeykatsuro.pzz.data.entities.PizzaVariant

fun ErrorType?.toLocalizedString(context: Context): String {
    val res = when (this) {
        ErrorType.IO -> R.string.error_io
        ErrorType.Network -> R.string.error_network
        ErrorType.NetworkConnection -> R.string.error_connection
        ErrorType.JsonParsing -> R.string.error_mapping
        ErrorType.Timeout -> R.string.error_timeout
        ErrorType.Unauthorized -> R.string.error_unauthorized
        ErrorType.Unclassified -> R.string.error_unclassified
        null -> null
    }
    return res?.let { context.getString(it) }.orEmpty()
}

fun PizzaVariant.Size?.toLocalizedString(context: Context): String {
    val res = when (this) {
        PizzaVariant.Size.Big -> R.string.pizza_size_big
        PizzaVariant.Size.Medium -> R.string.pizza_size_medium
        PizzaVariant.Size.Thin -> R.string.pizza_size_thin
        PizzaVariant.Size.Unknown -> R.string.pizza_size_unknown
        null -> null
    }
    return res?.let { context.getString(it) }.orEmpty()
}


