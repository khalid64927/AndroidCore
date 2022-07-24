/*
 * Copyright 2020 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Resource
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.vo.Repositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class RepoViewModel@Inject constructor(val repository: BaseRepository) : BaseViewModel() {
    val _items = MutableLiveData<Resource<List<Repositories>>>().apply { value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait") }

    // here we are getting from DB if not then network
    fun getRepoList() {
        Timber.d("getRepos :: ${Thread.currentThread().getName()}")
        _items.value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait")
        EspressoIdlingResource.increment() // Set app as busy.
        viewModelScope.launch {
            val repoResult = repository.getRepositories()
            EspressoIdlingResource.decrement() // Set app as idle.
            if (repoResult is Success) {
                getRepoListSuccess(repoResult.data)
                return@launch
            }
            // unsafe cast operator is ok to use as it can never be Loading state
            val error_ = repoResult as Result.Error_
            getRepoListFailed(error_.exception)
        }
        Timber.d("getRepos end")
    }

    fun getRepoListSuccess(list: List<Repositories>) {
        Timber.d(" success$list")
        Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        _items.value = Resource<List<Repositories>>(Status.SUCCESS, list, "success yay !")
    }

    private fun getRepoListFailed(error_: Exception) {
        Timber.d(" Error$error_")
        _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), error_.toString())
        Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
    }

    // here we are only getting data over the network
    fun forcedRefresh() {
        EspressoIdlingResource.increment() // Set app as busy.
        viewModelScope.launch {
            val repoResult = repository.fetchRepos()
            EspressoIdlingResource.decrement() // Set app as idle.
            if (repoResult is Success) {
                fetchSuccess(repoResult.data)
                return@launch
            }
            val error_ = repoResult as Result.Error_
            fetchFailed(error_.exception)
        }
    }

    private fun fetchSuccess(list: List<Repositories>) {
        Timber.d(" success$list")
        Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        _items.value = Resource<List<Repositories>>(Status.SUCCESS, list, "success yay !")
    }

    private fun fetchFailed(error_: Exception) {
        Timber.d(" Error$error_.toString()")
        _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), error_.toString())
        Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
    }
}
