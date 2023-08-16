package com.khalid.hamid.githubrepos.network.local

import com.khalid.hamid.githubrepos.db.ProductCategoriesDao
import com.khalid.hamid.githubrepos.db.ProductDao
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import timber.log.Timber
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val categoriesDao: ProductCategoriesDao,
    private val productDao: ProductDao){
    suspend fun getProductCategories(): ProductCategoriesList  {
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

    internal suspend fun insertProducts(products: ProductList){
        productDao.insertProductItems(products)
    }
    internal suspend fun insertCategories(categories: ProductCategoriesList){
        categoriesDao.insertProductCategories(categories)
    }
}