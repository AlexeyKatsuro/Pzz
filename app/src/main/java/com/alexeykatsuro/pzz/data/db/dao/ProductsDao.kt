package com.alexeykatsuro.pzz.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexeykatsuro.pzz.data.db.entries.ProductEntry
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ProductsDao : BaseEntryDao<ProductEntry> {

    @Query("SELECT * FROM basket_product")
    abstract fun getProductsFlow(): Flow<List<ProductEntry>>

    @Query("DELETE FROM basket_product")
    abstract suspend fun deleteAll()
}