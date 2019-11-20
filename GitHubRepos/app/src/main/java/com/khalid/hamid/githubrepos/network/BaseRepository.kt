package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.vo.Repositories
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class BaseRepository(private val baseDataSource: BaseDataSource): BaseDataSource {

    override suspend fun getRepositories(): Result<List<Repositories>> {
        Timber.d("getRepositories")
        return baseDataSource.getRepositories()
    }


    override suspend fun fetchRepos(): Result<List<Repositories>> {
        Timber.d("getRepositories")
        return baseDataSource.getRepositories()
    }

}