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
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TimelineViewModel @Inject constructor(
    val baseRepository: BaseRepository,
    val perf: Prefs
) : BaseViewModel() {

    val mainTimelineEventLiveData: LiveData<TimelineEvent>
        get() = _mainTimelineEventLiveData
    private val _mainTimelineEventLiveData = MutableLiveData<TimelineEvent>()

    init {
        getTimelineData()
    }

    private fun getTimelineData() {
        launchAsyncAPI {
            baseRepository.run {
                val categoriesResult = fetchProductCategories("https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/master.json")
                if (categoriesResult is Result.Failure) {
                    Timber.d("Failed to get master data")
                    // Return failure
                    return@launchAsyncAPI
                }

                val categoriesList = (categoriesResult as Result.Success).data
                Timber.d("categoriesResult $categoriesList")
                val url = categoriesList.first().data
                fetchProductForCategory(url).onSuccess {
                    _mainTimelineEventLiveData.value = ReceivedProducts(categoriesList, it)
                }.onError {
                    Timber.d("Failed to get Tab data")
                    _mainTimelineEventLiveData.value = FailedToFetchProducts(it.message)
                }
            }
        }
    }
}

sealed class TimelineEvent
data class ReceivedProducts(
    val categoriesList: ProductCategoriesList,
    val productList: ProductList
) : TimelineEvent()
data class FailedToFetchProducts(
    val message: String? = "Failed to fetch products"
) : TimelineEvent()
