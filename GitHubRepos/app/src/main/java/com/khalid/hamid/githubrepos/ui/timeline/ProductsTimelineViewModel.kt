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
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsTimelineViewModel @Inject constructor(
    val baseRepository: BaseRepository
) : BaseViewModel() {

    val productTimelineEventLiveData: LiveData<ProductsTimelineEvent>
        get() = _productTimelineEventLiveData
    private val _productTimelineEventLiveData = MutableLiveData<ProductsTimelineEvent>()

    fun getTimelineProducts(categoryId: String) {
        launchAsyncAPI ({
            _productTimelineEventLiveData.value = FailedToFetchTimelineProducts(it.message)
        }){
            val products = baseRepository.getProductForCategory(categoryId)
            _productTimelineEventLiveData.value = ReceivedProductsEvent(products)
        }
    }
}

sealed class ProductsTimelineEvent
internal data class ReceivedProductsEvent(
    val productList: ProductList
) : ProductsTimelineEvent()
internal data class FailedToFetchTimelineProducts(
    val message: String? = "Failed to fetch products"
) : ProductsTimelineEvent()
