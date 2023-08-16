package com.khalid.hamid.githubrepos.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesItem

@Dao
interface ProductCategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductCategories(repositories: List<ProductCategoriesItem>)
    @Query("SELECT * FROM categories")
    suspend fun getProductCategories(): List<ProductCategoriesItem>
}