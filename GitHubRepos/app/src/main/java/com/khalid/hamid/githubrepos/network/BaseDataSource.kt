package com.khalid.hamid.githubrepos.network

import androidx.annotation.WorkerThread
import com.khalid.hamid.githubrepos.vo.Repositories

interface BaseDataSource {

    @WorkerThread
    suspend fun getRepositories() : Result<List<Repositories>>

    @WorkerThread
    suspend fun fetchRepos() : Result<List<Repositories>>

}