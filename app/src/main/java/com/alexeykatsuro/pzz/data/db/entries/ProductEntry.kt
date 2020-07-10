package com.alexeykatsuro.pzz.data.db.entries

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "basket_product")
data class ProductEntry(
    @PrimaryKey(autoGenerate = true) override val localId: Long,
    @ColumnInfo(name = "server_id") val serverId: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "size") val size: String? = null
) : BaseEntry