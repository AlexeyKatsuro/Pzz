package com.alexeykatsuro.pzz.utils.delegates

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * Implementation of lazy that is not thread safe. Useful when you know what thread you will be
 * executing on and are not worried about synchronization.
 */
inline fun <reified T> lazyFast(crossinline operation: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE) {
        operation()
    }

inline fun <reified T> Fragment.lazyArg(crossinline operation: (Bundle) -> T): Lazy<T> = lazyFast {
    val args = arguments ?: throw IllegalStateException("Missing arguments!")
    operation(args)
}

inline fun <reified T : Any> Fragment.lazyArg(key: String): Lazy<T> = lazyFast {
    val args = arguments ?: throw IllegalStateException("Missing arguments!")
    @Suppress("UNCHECKED_CAST")
    args.get(key) as T
}