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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    val baseDataSource: BaseDataSource,
    val perf: Prefs
) : BaseViewModel() {

    val mainTimelineEventLiveData: LiveData<TimelineEvent>
        get() = _mainTimelineEventLiveData
    private val _mainTimelineEventLiveData = MutableLiveData<TimelineEvent>()

    init {
        getTimelineData()
    }

    private fun getTimelineData() {
        launchAsyncAPI({
            _mainTimelineEventLiveData.value = FailedToFetchProducts(it.message)
        }) {
            baseDataSource.run {
                Timber.d("launchAsyncAPI")
                val categoriesResult = getProductCategories()
                val categoryName = categoriesResult.firstOrNull()?.name ?: ""
                val productsResult = getAllProducts()
                val filteredProductResult = productsResult.filter { products -> products.categoryId == categoryName }
                val productList = ProductList()
                productList.addAll(filteredProductResult)
                _mainTimelineEventLiveData.value = ReceivedProducts(categoriesResult, productList)
            }
        }
    }
}

sealed class TimelineEvent
internal data class ReceivedProducts(
    val categoriesList: ProductCategoriesList,
    val productList: ProductList
) : TimelineEvent()
internal data class FailedToFetchProducts(
    val message: String? = "Failed to fetch products"
) : TimelineEvent()
