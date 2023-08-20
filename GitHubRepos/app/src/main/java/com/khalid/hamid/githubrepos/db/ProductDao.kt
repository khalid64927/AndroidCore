/*
 * Copyright 2023 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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