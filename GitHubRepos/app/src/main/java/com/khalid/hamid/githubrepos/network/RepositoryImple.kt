package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.vo.Repositories
import timber.log.Timber
import javax.inject.Inject

/**
 * This will return data from either DB or get from network
*/
class RepositoryImple @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource):BaseDataSource {

    override suspend fun getRepositories(): Result<List<Repositories>> {
        Timber.d("getRepositories ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        return fetchTasksFromRemoteOrLocal()
    }

    private suspend fun fetchTasksFromRemoteOrLocal(): Result<List<Repositories>>{
        Timber.d("fetchTasksFromRemoteOrLocal")

        // check
        val hasExpired = localDataSource.hasCacheExpired()

        if(!hasExpired){
            Timber.d("cache is valid ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            Timber.d("hasExpired is false retirnung DB data ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            return localDataSource.getRepositories()
        }

        // session has expired
        Timber.d("cache is invalid ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")

        return fetchRepos()
    }

    override suspend fun fetchRepos(): Result<List<Repositories>> {
        val fetchedData = remoteDataSource.fetchRespos()
        Timber.d("fetched data ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        when(fetchedData){
            is Success -> {
                Timber.d("Success ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                localDataSource.saveData(fetchedData.data)
                val savedData = localDataSource.getRepos()
                Timber.d(" saved data in DB ${savedData}")
                return localDataSource.getRepositories()
            }

            else -> {
                Timber.d("Error ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                return fetchedData
            }
        }

    }
}