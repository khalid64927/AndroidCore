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
import com.khalid.hamid.githubrepos.vo.GitRepos
import java.lang.Exception
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import retrofit2.Response

@RunWith(JUnit4::class)
class RemoteDataSourceTest {

    lateinit var subject: RemoteDataSource
    lateinit var service: GitHubService

    @Before
    fun setUp() {
        service = Mockito.mock(GitHubService::class.java)
        subject = RemoteDataSource(service)
    }

    @Test
    fun `verify when fetchRepos is called data is received`() {
        val default = GitRepos("", emptyList(), 0)
        var response = Mockito.mock(Response::class.java)

        Mockito.`when`(response.isSuccessful).thenReturn(true)
        Mockito.`when`(response.body()).thenReturn(default)

        runBlocking {
            Mockito.`when`(service.fetchRepos()).thenReturn(response as Response<GitRepos>)
        }

        val result = runBlocking {
            subject.fetchRepos()
        }
        assertEquals(result, Result.Success(default))
    }

    @Test
    fun `verify when fetchRepos is called error is received`() {
        // Given
        val default = GitRepos("", emptyList(), 0)
        var response = Mockito.mock(Response::class.java)

        When calling response.isSuccessful itReturns false
        When calling response.body() itReturns default

        runBlocking {
            When calling service.fetchRepos() itReturns response as Response<GitRepos>
        }

        // When
        val result = runBlocking {
            subject.fetchRepos()
        }

        // Then
        assertEquals(result, Result.Success(default))
    }

    @Test
    fun `verify when fetchRepos is called exception is received`() {
        // Given
        val default = GitRepos("", emptyList(), 0)
        var response = Mockito.mock(Response::class.java)
        val exception = "Unknown error"

        When calling response.isSuccessful itReturns false
        When calling response.body() itReturns "exception"

        runBlocking {
            When calling service.fetchRepos() itReturns response as Response<GitRepos>
        }

        var result: Result<GitRepos>? = null
        runBlocking {
            // When
            result = subject.fetchRepos()
        }

        // Then
        assert(result is Result.Error_)
        val errorType: Exception = (result as Result.Error_).exception

        assert(errorType.message == exception)
    }
}
