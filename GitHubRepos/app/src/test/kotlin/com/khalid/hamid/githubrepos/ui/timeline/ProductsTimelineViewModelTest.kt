package com.khalid.hamid.githubrepos.ui.timeline

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.testing.getOrAwaitValue
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import com.khalid.hamid.githubrepos.utils.parseJsonFromResourceInTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ProductsTimelineViewModelTest: BaseUnitTest() {

    var subject: ProductsTimelineViewModel? = null

    @MockK
    lateinit var localDataSource: LocalDataSource

    @MockK
    lateinit var remoteDataSource: RemoteDataSource

    override fun before() {
        super.before()
    }

    @Test
    fun `verify that we get mens products data successfully`(){
        // Given
        var baseRepository = BaseRepository(remoteDataSource, localDataSource)
        val mensProductList: ProductList = parseJsonFromResourceInTest(this.javaClass.classLoader, "mens_products.json")
        // setting manually which is done by sync
        mensProductList.forEach { it-> it.categoryId = "Men" }
        val productList = ProductList()
        productList.addAll(mensProductList)
        coEvery { localDataSource.getProductForCategory("Mens") } returns productList

        // When
        subject = ProductsTimelineViewModel(baseRepository)
        subject?.getTimelineProducts("Mens", )

        // Then
        val resultLD = subject?.productTimelineEventLiveData?.getOrAwaitValue()
        assertTrue(resultLD is ReceivedProductsEvent)
        assertTrue((resultLD as ReceivedProductsEvent).productList.size == 50)

    }

    @Test
    fun `verify that we get empty timeline mens products data`(){
        // Given
        var baseRepository = BaseRepository(remoteDataSource, localDataSource)
        val distinctElement = ProductList()
        coEvery { localDataSource.getProductForCategory("Mens") } returns distinctElement

        // When
        subject = ProductsTimelineViewModel(baseRepository)
        subject?.getTimelineProducts("Mens")

        // Then
        val resultLD = subject?.productTimelineEventLiveData?.getOrAwaitValue()
        assertTrue(resultLD is ReceivedProductsEvent)
        assertTrue((resultLD as ReceivedProductsEvent).productList.size == 0)

    }

}