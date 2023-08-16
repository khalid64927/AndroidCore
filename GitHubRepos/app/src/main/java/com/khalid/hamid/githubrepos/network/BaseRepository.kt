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

package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utilities.extentions.EspressoIdlingResource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@OpenForTesting
@Singleton
class BaseRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseDataSource {

    override var isSyncCompleted = false
    override var isSyncing = false
    var async: Deferred<Unit>? = null

    override suspend fun getProductCategories(): ProductCategoriesList {
        async?.run {
            await()
        }
        return localDataSource.getProductCategories()
    }

    override suspend fun getProductForCategory(categoryId: String): ProductList {
        return localDataSource.getProductForCategory(categoryId)
    }

    override suspend fun getAllProducts(): ProductList {
        async?.run {
            await()
        }
        return localDataSource.getAllProducts()
    }
    override suspend fun fetchProductCategories(url: String): Result<ProductCategoriesList> {
        return remoteDataSource.fetchProductCategories(url)
    }

    override suspend fun fetchProductForCategory(url: String): Result<ProductList> {
        return remoteDataSource.fetchProductForCategory(url)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun sync() {
        async = GlobalScope.async(
            CoroutineExceptionHandler { coroutineContext, throwable ->
                EspressoIdlingResource.decrement()
                Timber.e(throwable)
                throwable.message?.run {
                    isSyncing = false
                    isSyncCompleted = false
                }
            }
        ) {
            isSyncing = true
            val categoriesResult = remoteDataSource.fetchProductCategories(Endpoints.TIMELINE_CATEGORIES)
            if (categoriesResult.succeeded) {
                val categoriesList = (categoriesResult as Result.Success).data
                localDataSource.insertCategories(categoriesList)
                categoriesList.forEach { category ->
                    remoteDataSource.fetchProductForCategory(category.data).onSuccess {
                        it.forEach { product ->
                            product.categoryId = category.name
                        }
                        localDataSource.insertProducts(it)
                    }
                }
                isSyncing = false
                isSyncCompleted = false
            }
        }
    }
}
