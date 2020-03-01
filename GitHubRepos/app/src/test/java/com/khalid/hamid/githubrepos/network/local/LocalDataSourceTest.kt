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

package com.khalid.hamid.githubrepos.network.local

import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class LocalDataSourceTest {

    lateinit var dao: RepoDao
    lateinit var pref: Prefs

    lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        dao = Mockito.mock(RepoDao::class.java)
        pref = Mockito.mock(Prefs::class.java)
        localDataSource = LocalDataSource(dao, pref)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getTWOHOURS() {
    }

    @Test
    fun getRepositories_Empty() {
        val list: List<Repositories>
        runBlocking {
            Mockito.`when`(dao.getRepoList()).thenReturn(emptyList())
        }
        list = runBlocking {
            localDataSource.getRepos()
        }
        Assert.assertEquals(list, emptyList<Repositories>())
        Assert.assertTrue(list.size == 0)
    }

    @Test
    fun getRepositories_HasValue() {
        val list: ArrayList<Repositories> = ArrayList()
        list.add(Repositories())
        runBlocking {
            Mockito.`when`(dao.getRepoList()).thenReturn(list)
        }
        val listFromFunc = runBlocking {
            localDataSource.getRepos()
        }
        Assert.assertTrue(listFromFunc.size == 1)
    }

    @Test
    fun hasCacheExpired_True() {
        Mockito.`when`(pref.cachedTime).thenReturn("")
        Assert.assertTrue(localDataSource.hasCacheExpired())
    }

    @Test
    fun hasCacheExpired_False() {
        val validtime = System.currentTimeMillis()
        Mockito.`when`(pref.cachedTime).thenReturn("" + validtime)
        Assert.assertFalse(localDataSource.hasCacheExpired())
    }

    @Test
    fun saveData_Success() {
    }

    @Test
    fun saveData_Failed() {
    }

    @Test
    fun getRepos_Success() {
        runBlocking {
            Mockito.`when`(dao.getRepoList()).thenReturn(emptyList())
        }
        val list = runBlocking {
            localDataSource.getRepos()
        }
        Assert.assertEquals(list, emptyList<Repositories>())
    }

    @Test
    fun getRepos_Failed() {
        val list = runBlocking {
            localDataSource.getRepos()
        }
        Assert.assertNotEquals(list, emptyList<Repositories>())
    }

    @Test
    fun fetchRepos_Success() {
    }

    @Test
    fun fetchRepos_Failed() {
    }
}
