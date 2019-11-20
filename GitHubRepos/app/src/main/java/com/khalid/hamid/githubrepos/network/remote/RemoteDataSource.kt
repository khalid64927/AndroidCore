package com.khalid.hamid.githubrepos.network.remote

import com.khalid.hamid.githubrepos.network.GitHubService
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.network.Result.Error_
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.vo.Repositories
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubService: GitHubService) {

    suspend fun fetchRespos() : Result<List<Repositories>> {
        Timber.d("fetchRespos")
        try {
            val dataResponse = gitHubService.getRepositories()
            Timber.d("data response is  " + dataResponse.body().toString())

            if (dataResponse.isSuccessful) {
                Timber.d("dataResponse.isSuccessful")

                return Success(dataResponse?.body() ?: emptyList())
            } else {
                Timber.d("dataResponse.Error_")
                return Error_(Exception(dataResponse?.message() ?: "Unknown error"))
            }
        }catch (httpException: HttpException){
            Timber.d("dataResponse.Error_")
            httpException.printStackTrace()
            return Error_(httpException)
        }catch (unkown: Exception){
            Timber.d("dataResponse.Error_")
            unkown.printStackTrace()
            return Error_(unkown)

        }

    }


}