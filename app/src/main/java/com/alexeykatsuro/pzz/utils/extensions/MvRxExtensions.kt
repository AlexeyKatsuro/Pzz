@file:JvmName("AsyncUtils")

package com.alexeykatsuro.pzz.utils.extensions

import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.*
import com.alexeykatsuro.pzz.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun <T> Flow<InvokeStatus<T>>.asAsync(): Flow<Async<T>> =
    map {
        when (it) {
            InvokeStarted -> Loading()
            is InvokeSuccess -> Success(it.data)
            is InvokeError -> Fail<T>(it.throwable)
        }
    }

fun <T> Flow<InvokeStatus<T>>.asAsync(action: suspend (Async<T>) -> Unit): Flow<Async<T>> =
    asAsync().onEach(action)

fun <T> Flow<Async<T>>.trackError(eventBus: MutableLiveData<Event<Throwable>>): Flow<Async<T>> =
    onEach { status ->
        if (status is Fail) {
            eventBus.postValue(Event(status.error))
        }
    }

inline fun <T> Flow<Async<T>>.trackLoading(
    crossinline action: suspend (loading: Boolean) -> Unit
): Flow<Async<T>> =
    onEach { status ->
        action(status !is Loading)
    }

inline fun <T> Async<T>.onSuccess(action: (T) -> Unit) {
    if (this is Success) {
        action(invoke())
    }
}

inline fun Async<*>.onFail(action: (Throwable) -> Unit) {
    if (this is Fail) {
        action(error)
    }
}

fun <T> List<T>.upsert(value: T, finder: (T) -> Boolean) = indexOfFirst(finder).let { index ->
    if (index >= 0) copy(index, value) else this + value
}

fun <T> List<T>.copy(index: Int, item: T): List<T> =
    toMutableList().apply { set(index, item) }


fun Async<*>.isLoading(): Boolean = this is Loading
fun Async<*>.isNotLoading(): Boolean = this !is Loading

fun Async<*>.isFail(): Boolean = this is Fail

fun Async<*>.isSuccess(): Boolean = this is Success

inline fun <T, R> Async<T>.map(transform: (T) -> R): Async<R> {
    @Suppress("UNCHECKED_CAST")
    return when (this) {
        is Success -> Success(transform(invoke()))
        is Fail -> this as Async<R>
        is Loading -> this as Async<R>
        is Uninitialized -> this
    }
}