package com.khalid.hamid.githubrepos.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsTimelineViewModel @Inject constructor(
    val baseRepository: BaseRepository
): BaseViewModel() {

    val productTimelineEventLiveData: LiveData<ProductsTimelineEvent>
        get() = _productTimelineEventLiveData
    private val _productTimelineEventLiveData = MutableLiveData<ProductsTimelineEvent>()


    fun getTimelineProducts(){
        launchAsyncAPI {
            // TODO
        }
    }

}

sealed class ProductsTimelineEvent