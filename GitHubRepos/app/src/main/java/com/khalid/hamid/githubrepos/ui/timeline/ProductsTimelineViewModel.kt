package com.khalid.hamid.githubrepos.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsTimelineViewModel @Inject constructor(
    val baseRepository: BaseRepository
): BaseViewModel() {

    val productTimelineEventLiveData: LiveData<ProductsTimelineEvent>
        get() = _productTimelineEventLiveData
    private val _productTimelineEventLiveData = MutableLiveData<ProductsTimelineEvent>()


    fun getTimelineProducts(url: String){
        launchAsyncAPI {
            baseRepository.fetchProductForCategory(url).
            onSuccess {
                _productTimelineEventLiveData.value = ReceivedProductsEvent(it)
            }.onError {
                _productTimelineEventLiveData.value = FailedToFetchTimelineProducts()
            }
        }
    }

}

sealed class ProductsTimelineEvent
data class ReceivedProductsEvent(
    val productList: ProductList
): ProductsTimelineEvent()
data class FailedToFetchTimelineProducts(
    val message: String? ="Failed to fetch products"): ProductsTimelineEvent()

