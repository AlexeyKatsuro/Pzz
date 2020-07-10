package com.alexeykatsuro.pzz.utils

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.util.concurrent.atomic.AtomicInteger

class ObservableLoadingCounter {
    private val _count = AtomicInteger()
    val count: Int get() = _count.get()

    private val loadingState = ConflatedBroadcastChannel(_count.get())

    val observable: Flow<Boolean>
        get() = loadingState.asFlow().map { it > 0 }

    fun addLoader() {
        loadingState.sendBlocking(_count.incrementAndGet())
    }

    fun removeLoader() {
        loadingState.sendBlocking(_count.decrementAndGet())
    }
}
