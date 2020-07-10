package com.alexeykatsuro.pzz.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexeykatsuro.pzz.data.db.dao.ProductsDao
import com.alexeykatsuro.pzz.data.db.entries.ProductEntry


@Database(
    entities = [
        ProductEntry::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RoomPzzDatabase : RoomDatabase(), PzzDataBase

interface PzzDataBase {
    fun productsDao(): ProductsDao
}