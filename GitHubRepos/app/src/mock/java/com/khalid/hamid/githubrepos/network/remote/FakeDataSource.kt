/*
 * Copyright 2021 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.network.remote

import android.app.Application
import com.google.gson.Gson
import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utilities.extentions.runSafe
import javax.inject.Inject

class FakeDataSource @Inject constructor(val context: Application) : BaseDataSource {
    val gson = Gson()

    override var isSyncCompleted = false
    override suspend fun fetchProductCategories(url: String): Result<ProductCategoriesList> {
        return Result.Success(ProductCategoriesList())
    }

    override suspend fun fetchProductForCategory(url: String): Result<ProductList> {
        return Result.Success(ProductList())
    }

    override suspend fun getProductCategories(): ProductCategoriesList {
        return ProductCategoriesList()
    }

    override suspend fun getProductForCategory(categoryId: String): ProductList {
        return ProductList()
    }

    override suspend fun getAllProducts(): ProductList {
        return ProductList()
    }

    override fun sync() {
        // no-op
    }

    override var isSyncing = false

    private inline fun <reified T> parse(jsonPath: String): T? {
        var response: T? = null
        runSafe {
            val jsonString = context.assets.open(jsonPath)
                .bufferedReader()
                .use { it.readText() }
            response = gson.fromJson(jsonString, T::class.java)
        }
        return response
    }
}
