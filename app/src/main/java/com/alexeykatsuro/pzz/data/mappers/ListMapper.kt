package com.alexeykatsuro.pzz.data.mappers

import com.alexeykatsuro.pzz.data.ErrorType
import com.alexeykatsuro.pzz.data.throwThrowableResult
import timber.log.Timber


typealias ListMapper<F, T> = Mapper<List<F>, List<T>>
typealias ListMapper2Source<F1, F2, T> = Mapper2Source<List<F1>, F2, List<T>>

fun <F, T> Mapper<F, T>.toListMapper(): ListMapper<F, T> = object : ListMapper<F, T> {
    override fun map(from: List<F>): List<T> = from.map(this@toListMapper::map)
}


fun <F1, F2, T> Mapper2Source<F1, F2, T>.toListMapper(): ListMapper2Source<F1, F2, T> =
    object : ListMapper2Source<F1, F2, T> {
        override fun map(from1: List<F1>, from2: F2): List<T> = from1.map {
            this@toListMapper.map(it, from2)
        }
    }


/**
 * ListSafeMapper prevent crash during response mapping when any invalid element throws
 * any kind of exception. Such elements will simply be ignored.
 *
 * Note: Mapper can throw [ThrowableResult] if mapping of whole list fished with error
 */
fun <F, T : Any> Mapper<F, T>.toListSafeMapper(): ListMapper<F, T> = object : ListMapper<F, T> {
    override fun map(from: List<F>): List<T> = from.mapNotNull { item ->
        try {
            this@toListSafeMapper.map(item)
        } catch (ex: Exception) {
            Timber.e("Error parsing of $item")
            Timber.e(ex)
            null
        }
    }.also { result -> throwIfAllNull(from, result) }

    fun throwIfAllNull(fromList: List<F>, resultList: List<T>) {
        if (fromList.isNotEmpty() && resultList.isEmpty()) throwThrowableResult(ErrorType.JsonParsing)
    }
}
