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
