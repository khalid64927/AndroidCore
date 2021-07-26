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

package com.khalid.hamid.githubrepos.network

import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.vo.GitRepos
import java.lang.Exception
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class RepositoryImpleTest {
    lateinit var remoteDataSource: RemoteDataSource
    lateinit var subject: RepositoryImple
    val default = GitRepos("", emptyList(), 0)

    @Before
    fun setUp() {
        remoteDataSource = mock(RemoteDataSource::class.java)
        subject = RepositoryImple(remoteDataSource)
    }

    @Test
    fun `verify that repo is called`() {
        // Given
        runBlocking {
            When calling remoteDataSource.fetchRepos() itReturns Result.Success(default)
        }

        runBlocking {
            // When
            subject.fetchRepos()
        }
        runBlocking {
            // Then
            Verify on remoteDataSource that remoteDataSource.fetchRepos() was called
        }
    }

    @Test
    fun `verify success response is received when fetchRepos is called`() {
        // Given
        runBlocking {
            When calling remoteDataSource.fetchRepos() itReturns Result.Success(default)
        }

        // When
        runBlocking {
            val result = subject.fetchRepos()
            // Then
            assert(result is Result.Success)
        }
    }

    @Test
    fun `verify error response is received when fetchRepos is called`() {
        // Given
        runBlocking {
            When calling remoteDataSource.fetchRepos() itReturns Result.Error_(Exception("Some error"))
        }

        runBlocking {
            // When
            val result = subject.fetchRepos()
            // Then
            assert(result is Result.Error_)
        }
    }
}
