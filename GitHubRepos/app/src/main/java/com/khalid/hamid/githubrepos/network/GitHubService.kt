package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.vo.Repositories
import retrofit2.Response
import retrofit2.http.GET

interface GitHubService {

    @GET("/repositories")
    suspend fun getRepositories() : Response<List<Repositories>>

}