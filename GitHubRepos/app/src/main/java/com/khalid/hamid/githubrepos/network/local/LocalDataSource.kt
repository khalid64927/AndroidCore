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

package com.khalid.hamid.githubrepos.network.local

import com.khalid.hamid.githubrepos.db.ProductCategoriesDao
import com.khalid.hamid.githubrepos.db.ProductDao
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val categoriesDao: ProductCategoriesDao,
    private val productDao: ProductDao
) {
    suspend fun getProductCategories(): ProductCategoriesList {
        val categories = categoriesDao.getProductCategories()
        val categoriesList = ProductCategoriesList()
        categoriesList.addAll(categories)
        return categoriesList
    }

    suspend fun getProductForCategory(categoryId: String): ProductList {
        val products = productDao.getProductForCategory(categoryId = categoryId)
        val productList = ProductList()
        productList.addAll(products)
        return productList
    }

    suspend fun getAllProducts(): ProductList {
        val products = productDao.getProductItems()
        val productList = ProductList()
        productList.addAll(products)
        return productList
    }

    internal suspend fun insertProducts(products: ProductList) {
        productDao.insertProductItems(products)
    }
    internal suspend fun insertCategories(categories: ProductCategoriesList) {
        categoriesDao.insertProductCategories(categories)
    }
}
