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

package com.khalid.hamid.githubrepos.ui.timeline

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.testing.getOrAwaitValue
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import com.khalid.hamid.githubrepos.utils.parseJsonFromResourceInTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase
import org.junit.Test

class TimelineViewModelTest : BaseUnitTest() {

    var subject: TimelineViewModel? = null

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    override fun before() {
        super.before()
    }

    @Test
    fun `verify that we get timeline data successfully`() {
        // Given
        var baseRepository = BaseRepository(remoteDataSource, localDataSource)
        val productCategoriesList: ProductCategoriesList =
            parseJsonFromResourceInTest(this.javaClass.classLoader, "product_categories.json")

        val mensProductList: ProductList = parseJsonFromResourceInTest(this.javaClass.classLoader, "mens_products.json")
        // setting manually which is done by sync
        mensProductList.forEach { it -> it.categoryId = "Men" }
        val womensProductList: ProductList = parseJsonFromResourceInTest(this.javaClass.classLoader, "womens_products.json")
        // setting manually which is done by sync
        mensProductList.forEach { it -> it.categoryId = "Women" }
        val allProductList: ProductList = parseJsonFromResourceInTest(this.javaClass.classLoader, "all_products.json")
        // setting manually which is done by sync
        mensProductList.forEach { it -> it.categoryId = "All" }

        val productList = ProductList()
        productList.addAll(mensProductList)
        productList.addAll(womensProductList)
        productList.addAll(allProductList)
        val distinctElement: ProductList = ProductList()
        distinctElement.addAll(productList.distinctBy { it.id })

        coEvery { localDataSource.getProductCategories() } returns productCategoriesList
        coEvery { localDataSource.getAllProducts() } returns distinctElement

        // When
        subject = TimelineViewModel(baseRepository, mockk())

        // Then
        val resultLD = subject?.mainTimelineEventLiveData?.getOrAwaitValue()
        TestCase.assertTrue(resultLD is ReceivedProducts)
        TestCase.assertTrue((resultLD as ReceivedProducts).categoriesList.size == 3)
        TestCase.assertTrue((resultLD).productList.size == 50)
    }

    @Test
    fun `verify that we get empty timeline data`() {
        // Given
        var baseRepository = BaseRepository(remoteDataSource, localDataSource)
        val productCategoriesList = ProductCategoriesList()
        val distinctElement = ProductList()
        coEvery { localDataSource.getProductCategories() } returns productCategoriesList
        coEvery { localDataSource.getAllProducts() } returns distinctElement

        // When
        subject = TimelineViewModel(baseRepository, mockk())

        // Then
        val resultLD = subject?.mainTimelineEventLiveData?.getOrAwaitValue()
        TestCase.assertTrue(resultLD is ReceivedProducts)
        TestCase.assertTrue((resultLD as ReceivedProducts).categoriesList.size == 0)
        TestCase.assertTrue((resultLD).productList.size == 0)
    }
}
