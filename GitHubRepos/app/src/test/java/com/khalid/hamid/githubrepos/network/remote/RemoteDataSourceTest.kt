package com.khalid.hamid.githubrepos.network.remote

import com.khalid.hamid.githubrepos.network.GitHubService
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class RemoteDataSourceTest {


    lateinit var remoteDataSource: RemoteDataSource
    lateinit var service: GitHubService

    @Before
    fun setUp() {
        service = Mockito.mock(GitHubService::class.java)
        remoteDataSource = RemoteDataSource(service)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fetchRespos_Success() {
        val list = emptyList<Repositories>()
        var response = Mockito.mock(Response::class.java)

        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(list)

        runBlocking {
            Mockito.`when`(service.getRepositories()).thenReturn(response as Response<List<Repositories>>)
        }


        val result = runBlocking {
            remoteDataSource.fetchRespos()
        }

        assertEquals(result, Result.Success(list))

    }
}