package com.alexeykatsuro.pzz.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.*
import com.alexeykatsuro.pzz.utils.BaseNavEvent
import com.alexeykatsuro.pzz.utils.Event
import com.alexeykatsuro.pzz.utils.NavigateUp
import com.alexeykatsuro.pzz.utils.extensions.postEvent
import com.alexeykatsuro.pzz.utils.extensions.trackError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import timber.log.Timber

abstract class PzzViewModel<S : MvRxState>(initialState: S) :
    BaseMvRxViewModel<S>(initialState, debugMode = BuildConfig.DEBUG) {


    //TODO replace LiveData with single event bus by Flow
    protected val errorEventBus = MutableLiveData<Event<Throwable>>()
    val onErrorEvent: LiveData<Event<Throwable>>
        get() = errorEventBus

    protected val navigateEvent = MutableLiveData<Event<BaseNavEvent>>()
    val onNavigateEvent: LiveData<Event<BaseNavEvent>>
        get() = navigateEvent

    protected val messageResEvent = MutableLiveData<Event<Int>>()
    val onMessageResEvent: LiveData<Event<Int>>
        get() = messageResEvent

    protected suspend inline fun <T> Flow<T>.execute(
        crossinline stateReducer: S.(Async<T>) -> S
    ) = execute({ it }, stateReducer)

    protected suspend inline fun <T, V> Flow<T>.execute(
        crossinline mapper: (T) -> V,
        crossinline stateReducer: S.(Async<V>) -> S
    ) {
        setState { stateReducer(Loading()) }

        @Suppress("USELESS_CAST")
        return map { Success(mapper(it)) as Async<V> }
            .catch {
                Timber.d(it)
                emit(Fail(it))
            }.trackError(errorEventBus)
            .collect {
                setState {
                    stateReducer(it)
                }
            }
    }


    fun <T> Flow<T>.launch(): Job = launchIn(viewModelScope)

    fun onNavigateUp() {
        navigateEvent.postEvent(NavigateUp)
    }
}