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

import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RepositoryImpleTest {
    lateinit var localDataSource: LocalDataSource
    lateinit var remoteDataSource: RemoteDataSource
    lateinit var repositoryImple: RepositoryImple

    @Before
    fun setUp() {
        localDataSource = mock(LocalDataSource::class.java)
        remoteDataSource = mock(RemoteDataSource::class.java)
        repositoryImple = RepositoryImple(localDataSource, remoteDataSource)
    }

    @After
    fun tearDown() {
    }

    inline fun <reified T : Any> argumentCaptor() = ArgumentCaptor.forClass(T::class.java)

    @Test
    fun getRepositories_FirtTimeAccess() = runBlocking {
        `when`(localDataSource.hasCacheExpired()).thenReturn(true)
        val savedData = emptyList<Repositories>()
        `when`(remoteDataSource.fetchRepos()).thenReturn(Result.Success(savedData))
        repositoryImple.getRepositories()
        runBlocking {
            verify((remoteDataSource), times(1)).fetchRepos()
            verify((localDataSource), times(1)).saveData(savedData)
        }
    }

    @Test
    fun getRepositories_ConsecuentAccess() {
        `when`(localDataSource.hasCacheExpired()).thenReturn(false)
        val empty = emptyList<Repositories>()
        runBlocking {
            Mockito.`when`(localDataSource.getRepositories()).thenReturn(Result.Success(empty))
        }
        runBlocking {
            repositoryImple.getRepositories()
        }
        runBlocking {
            verify((localDataSource), times(1)).getRepositories()
            verify((remoteDataSource), never()).fetchRepos()
        }
    }

    @Test
    fun fetchRepos() {
    }
}
