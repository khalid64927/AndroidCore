package com.khalid.hamid.githubrepos.network.remote

import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.vo.Repositories
import javax.inject.Inject

class FakeDataSource @Inject constructor(private val repoDao: RepoDao): BaseDataSource  {


    override suspend fun getRepositories(): Result<List<Repositories>> {
        return Result.Success(emptyList())
    }

    override suspend fun fetchRepos(): Result<List<Repositories>> {
        return Result.Error_(Exception("Fake !"))
    }
}