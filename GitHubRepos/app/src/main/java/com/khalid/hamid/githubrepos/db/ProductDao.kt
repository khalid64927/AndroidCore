package com.khalid.hamid.githubrepos.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductsItem

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductItems(repositories: List<ProductsItem>)
    @Query("SELECT * FROM products")
    suspend fun getProductItems(): List<ProductsItem>

    @Query("SELECT * FROM products WHERE categoryId=:categoryId")
    suspend fun getProductForCategory(categoryId: String): List<ProductsItem>
}