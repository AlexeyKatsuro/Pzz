@file:Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")

package com.alexeykatsuro.pzz.data

import com.alexeykatsuro.pzz.data.mappers.MapFunction
import com.alexeykatsuro.pzz.data.mappers.Mapper
import com.alexeykatsuro.pzz.data.mappers.toMapper
import com.alexeykatsuro.pzz.utils.ResultErrorCatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRunner @Inject constructor(
    private val errorCatcher: ResultErrorCatcher
) {

    suspend fun <R, E> invoke(
        mapper: Mapper<R, E>,
        request: suspend () -> R
    ): Result<E> {
        return errorCatcher.catch {
            val response = request()
            mapper.map(response)
        }

    }

    suspend fun <R, E> invoke(
        mapper: MapFunction<R, E>,
        request: suspend () -> R
    ): Result<E> =
        invoke(mapper.toMapper(), request)


    suspend operator fun <R> invoke(request: suspend () -> R): Result<R> =
        invoke({ it }, request)

}