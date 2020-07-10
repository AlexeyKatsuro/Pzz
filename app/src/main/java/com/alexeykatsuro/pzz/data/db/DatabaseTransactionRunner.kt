/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.data.db

import androidx.room.withTransaction
import javax.inject.Inject


interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}

class RoomTransactionRunner @Inject constructor(private val db: RoomPzzDatabase) :
    DatabaseTransactionRunner {
    override suspend operator fun <T> invoke(block: suspend () -> T): T {
        return db.withTransaction {
            block()
        }
    }
}
