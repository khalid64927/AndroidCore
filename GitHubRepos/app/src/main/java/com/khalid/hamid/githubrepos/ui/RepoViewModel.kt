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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Resource
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
open class RepoViewModel@Inject constructor(private val repository: BaseRepository) : ViewModel() {
    val _items = MutableLiveData<Resource<List<Repositories>>>().apply { value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait") }
    private val error_item = Resource<List<Repositories>>(Status.ERROR, emptyList(), " error happned")
    // here we are getting from DB if not then network
    fun getRepoList() {
        Timber.d("getRepos :: " + Thread.currentThread().getName())
        _items.value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait")
        EspressoIdlingResource.increment() // Set app as busy.
        viewModelScope.launch {
            val repoResult = repository.getRepositories()
            if (repoResult is Success) {
                Timber.d(" success" + repoResult.data.toString())
                Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                _items.value = Resource<List<Repositories>>(Status.SUCCESS, repoResult.data, "success yay !")
            } else {
                Timber.d(" Error" + repoResult.toString())
                _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), repoResult.toString())
                Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            }
        }
        Timber.d("getRepos end")
    }

    // here we are only getting data over the network
    fun forcedRefresh() {
        EspressoIdlingResource.increment() // Set app as busy.

        viewModelScope.launch {
            val repoResult = repository.fetchRepos()
            if (repoResult is Success) {
                Timber.d(" success" + repoResult.data.toString())
                Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                _items.value = Resource<List<Repositories>>(Status.SUCCESS, repoResult.data, "success yay !")
            } else {
                Timber.d(" Error" + repoResult.toString())
                _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), repoResult.toString())
                Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            }
        }
    }
}
