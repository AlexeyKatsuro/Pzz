package com.alexeykatsuro.pzz.data.repository.basket

import com.alexeykatsuro.pzz.data.db.DatabaseTransactionRunner
import com.alexeykatsuro.pzz.data.db.dao.ProductsDao
import com.alexeykatsuro.pzz.data.db.mappers.ProductEntityMapper
import com.alexeykatsuro.pzz.data.db.mappers.toListMapper
import com.alexeykatsuro.pzz.data.entities.basket.Basket
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BasketStore @Inject constructor(
    private val productsDao: ProductsDao,
    private val productEntityMapper: ProductEntityMapper,
    private val transactionRunner: DatabaseTransactionRunner
) {
    private val productEntityListMapper = productEntityMapper.toListMapper()
    suspend fun updateBasket(basket: Basket) = transactionRunner {
        val entries = productEntityListMapper.toEntity(basket.products)
        productsDao.deleteAll()
        productsDao.insertAll(entries)
    }

    fun observeBasket(): Flow<Basket> =
        productsDao.getProductsFlow().map {
            Basket(productEntityListMapper.fromEntity(it))
        }
}