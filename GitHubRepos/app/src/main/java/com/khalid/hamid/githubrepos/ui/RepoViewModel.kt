package com.khalid.hamid.githubrepos.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khalid.hamid.githubrepos.network.Resource
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.vo.Repositories
import com.khalid.hamid.githubrepos.network.BaseRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
open class RepoViewModel@Inject constructor(private val repository: BaseRepository): ViewModel() {
    val _items = MutableLiveData<Resource<List<Repositories>>>().apply { value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait")}
    private val error_item = Resource<List<Repositories>>(Status.ERROR, emptyList(), " error happned")
    // here we are getting from DB if not then network
    fun getRepoList(){
        Timber.d("getRepos :: " + Thread.currentThread().getName())
        _items.value = Resource<List<Repositories>>(Status.LOADING, emptyList(), "wait")
        EspressoIdlingResource.increment() // Set app as busy.
        viewModelScope.launch {
            val repoResult = repository.getRepositories()
            if(repoResult is Success){
                Timber.d(" success"+repoResult.data.toString())
                Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                _items.value = Resource<List<Repositories>>(Status.SUCCESS, repoResult.data, "success yay !")
            }else {
                Timber.d(" Error"+ repoResult.toString())
                _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), repoResult.toString())
                Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            }
        }
        Timber.d("getRepos end")
    }

    // here we are only getting data over the network
    fun forcedRefresh(){
        EspressoIdlingResource.increment() // Set app as busy.

        viewModelScope.launch {
            val repoResult = repository.fetchRepos()
            if(repoResult is Success){
                Timber.d(" success"+repoResult.data.toString())
                Timber.d(" Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                _items.value = Resource<List<Repositories>>(Status.SUCCESS, repoResult.data, "success yay !")
            }else {
                Timber.d(" Error"+ repoResult.toString())
                _items.value = Resource<List<Repositories>>(Status.ERROR, emptyList(), repoResult.toString())
                Timber.d(" Error Expresso counter before ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                EspressoIdlingResource.decrement() // Set app as idle.
                Timber.d(" Error Expresso counter AFTER ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            }
        }

    }

}