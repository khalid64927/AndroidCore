package com.khalid.hamid.githubrepos.network.local

import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.vo.Repositories
import timber.log.Timber
import javax.inject.Inject


/**
 * This will always fetch data from database
 *
 */
open class LocalDataSource @Inject constructor(
    private val repoDao: RepoDao,
    private val pref: Prefs): BaseDataSource {

    val TWOHOURS : Long = 2 * 60 * 60 * 1000

    override suspend fun getRepositories(): Result<List<Repositories>> {
        return Success(getRepos() ?: emptyList<Repositories>())
    }

    fun hasCacheExpired() : Boolean{
        Timber.d("hasCacheExpired")
        if(pref.cachedTime.isEmpty()){
            Timber.d("no last saved time ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            return true
        }

        val currentTime = System.currentTimeMillis()
        val prevTime = pref.cachedTime.toLong()
        val elapsedTime = currentTime - prevTime
        if(elapsedTime >= TWOHOURS){
            Timber.d("cache expired ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
            return true
        }
        return false
    }

    suspend fun saveData(list: List<Repositories>){
        Timber.d(" saveData ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        pref.cachedTime = System.currentTimeMillis().toString()
        repoDao.insertRepos(list)
    }

    suspend fun getRepos(): List<Repositories>{
        Timber.d("getRepos ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        return repoDao.getRepoList()
    }

    override suspend fun fetchRepos(): Result<List<Repositories>> {
        return Result.Error_(Exception("Illegal call"))
    }

}