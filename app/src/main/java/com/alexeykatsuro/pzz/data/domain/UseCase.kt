/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alexeykatsuro.pzz.data.domain

import com.alexeykatsuro.pzz.data.ErrorType
import com.alexeykatsuro.pzz.data.ThrowableResult
import com.alexeykatsuro.pzz.utils.InvokeError
import com.alexeykatsuro.pzz.utils.InvokeStarted
import com.alexeykatsuro.pzz.utils.InvokeStatus
import com.alexeykatsuro.pzz.utils.InvokeSuccess
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.concurrent.TimeUnit


abstract class UseCase<P : Any, T> {

    abstract val dispatcher: CoroutineDispatcher

    operator fun invoke(params: P): Flow<InvokeStatus<T>> = flow {
        emit(InvokeStarted)
        try {
            val result = doWork(params)
            emit(InvokeSuccess(result))
        } catch (t: Throwable) {
            Timber.d(t)
            emit(InvokeError(t))
        }
    }.flowOn(dispatcher)

    protected abstract suspend fun doWork(params: P): T

}

abstract class WorkUseCase<in P> {
    protected abstract val scope: CoroutineScope

    operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): Flow<InvokeStatus<Unit>> {
        val channel = ConflatedBroadcastChannel<InvokeStatus<Unit>>()
        scope.launch {
            try {
                withTimeout(timeoutMs) {
                    channel.send(InvokeStarted)
                    try {
                        doWork(params)
                        channel.send(InvokeSuccess(Unit))
                    } catch (t: Throwable) {
                        Timber.d(t)
                        channel.send(InvokeError(t))
                    }
                }
            } catch (t: TimeoutCancellationException) {
                Timber.d(t)
                val throwable = ThrowableResult(cause = t, type = ErrorType.Timeout)
                channel.send(InvokeError(throwable))
            } finally {
                delay(400)
                channel.close()
            }
        }
        return channel.asFlow()
    }


    suspend fun executeSync(params: P) = withContext(scope.coroutineContext) { doWork(params) }

    protected abstract suspend fun doWork(params: P)

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

interface ObservableUseCase<T> {
    val dispatcher: CoroutineDispatcher
    fun observe(): Flow<T>
}

abstract class SuspendingWorkUseCase<P : Any, T : Any> : ObservableUseCase<T> {
    private val channel = ConflatedBroadcastChannel<T>()

    suspend operator fun invoke(params: P) = channel.send(doWork(params))

    abstract suspend fun doWork(params: P): T

    override fun observe(): Flow<T> = channel.asFlow()

}

abstract class SubjectUseCase<P : Any, T> : ObservableUseCase<T> {
    private val channel = ConflatedBroadcastChannel<P>()

    operator fun invoke(params: P) = channel.sendBlocking(params)

    protected abstract fun createObservable(params: P): Flow<T>

    override fun observe(): Flow<T> = channel.asFlow()
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
}

abstract class ResultUseCase<in P, R> {
    abstract val dispatcher: CoroutineDispatcher

    suspend operator fun invoke(params: P): R {
        return withContext(dispatcher) { doWork(params) }
    }

    protected abstract suspend fun doWork(params: P): R
}


operator fun WorkUseCase<Unit>.invoke() = invoke(Unit)
operator fun <T> UseCase<Unit, T>.invoke() = invoke(Unit)
suspend operator fun <T> ResultUseCase<Unit, T>.invoke() = invoke(Unit)
operator fun <T> SubjectUseCase<Unit, T>.invoke() = invoke(Unit)

fun <I : ObservableUseCase<T>, T> CoroutineScope.launchObserve(
    useCase: I,
    block: suspend (Flow<T>) -> Unit
) {
    launch(useCase.dispatcher) {
        block(useCase.observe())
    }
}
