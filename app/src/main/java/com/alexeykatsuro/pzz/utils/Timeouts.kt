/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils

import java.util.concurrent.TimeUnit

sealed class Timeouts {
    abstract val connect: Long
    abstract val read: Long
    abstract val write: Long
    abstract val timeUnit: TimeUnit

    object Default : Timeouts() {
        override val connect = 30L
        override val read = 60L // 1 min
        override val write = 60L
        override val timeUnit = TimeUnit.SECONDS
    }

    object AsanImza : Timeouts() {
        override val connect = 120L // 2 min
        override val read = 120L
        override val write = 120L
        override val timeUnit = TimeUnit.SECONDS
    }

    // For downloading extra large files
    object DeadlyBoring : Timeouts() {
        override val connect = 30L
        override val read = 600L // 10 min
        override val write = 600L
        override val timeUnit = TimeUnit.SECONDS
    }
}
