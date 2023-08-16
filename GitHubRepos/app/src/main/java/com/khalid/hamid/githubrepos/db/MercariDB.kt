package com.khalid.hamid.githubrepos.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesItem
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductsItem


@Database(
    entities = [
        ProductCategoriesItem::class,
        ProductsItem::class
    ],
    version = 1,
    exportSchema = true
)
abstract class MercariDB: RoomDatabase() {

    abstract fun getProductCategoriesDao(): ProductCategoriesDao
    abstract fun getProductDao(): ProductDao
}