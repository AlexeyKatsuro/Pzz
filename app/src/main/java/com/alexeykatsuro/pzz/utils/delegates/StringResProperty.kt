/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils.delegates

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

/**
 * Delegate for assign string from resource into [receiver].
 * @param receiver - the property in which will be assigned string from resource after [setValue].
 */
class StringResProperty(
    private val context: Context,
    private val receiver: KMutableProperty0<String>
) :
    ReadWriteProperty<Any, Int> {

    private var _value: Int? = 0

    override fun getValue(thisRef: Any, property: KProperty<*>): Int {
        return _value ?: throw IllegalStateException("Property must be initialized")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
        _value = value
        receiver.set(context.getString(value))
    }
}