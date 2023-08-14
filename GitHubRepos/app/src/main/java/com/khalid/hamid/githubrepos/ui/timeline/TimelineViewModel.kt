package com.khalid.hamid.githubrepos.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Endpoints
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
): BaseViewModel(){

    val mainTimelineEventLiveData: LiveData<TimelineEvent>
        get() = _mainTimelineEventLiveData
    private val _mainTimelineEventLiveData = MutableLiveData<TimelineEvent>()

    init {
        Timber.d("init == ")
        getTimelineData()
    }


    private fun getTimelineData(){
        Timber.d("getTimelineData ==== VM ")
        launchAsyncAPI {
            Timber.d("getTimelineData ==== VM === launchAsyncAPI ")
            baseRepository.run {
                val categoriesResult = fetchProductCategories(Endpoints.TIMELINE_CATEGORIES)
                if (categoriesResult is Result.Failure) {
                    // Return failure
                    return@launchAsyncAPI
                }
                val categoriesList = (categoriesResult as Result.Success).data
                val url = categoriesList.first().data
                fetchProductForCategory(url).onSuccess {
                    _mainTimelineEventLiveData.value = ReceivedProducts(categoriesList, it)
                }.onError {
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
): TimelineEvent()
data class FailedToFetchProducts(
    val message: String? ="Failed to fetch products"): TimelineEvent()
