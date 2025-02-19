package com.alexeykatsuro.pzz.utils.extensions

import androidx.lifecycle.MutableLiveData
import com.alexeykatsuro.pzz.utils.Event

fun <T> MutableLiveData<Event<T>>.postEvent(value: T) {
    postValue(Event(value))
}

fun MutableLiveData<Event<Unit>>.postEvent() {
    postValue(Event(Unit))
}


fun MutableLiveData<Event<Throwable>>.postToDoEvent(log: String = "") {
    postEvent(NotImplementedError("Under develop $log".trim()))
}