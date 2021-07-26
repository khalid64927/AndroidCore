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

package com.khalid.hamid.githubrepos.ui

import com.khalid.hamid.githubrepos.BaseUnitTest
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.getOrAwaitValue
import com.khalid.hamid.githubrepos.vo.GitRepos
import java.lang.Exception
import okhttp3.mockwebserver.MockWebServer
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RepoViewModelTest : BaseUnitTest() {

    private lateinit var mockWebServer: MockWebServer

    lateinit var subject: RepoViewModel

    @Mock
    lateinit var baseRepository: BaseRepository

    val default = GitRepos("", emptyList(), 0)

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subject = RepoViewModel(baseRepository)
    }

    @Test
    fun `verify getList returns success response`() {
        runBlockingTest {
            When calling baseRepository.fetchRepos() itReturns Result.Success(default)
            subject.getRepoList()
            pauseDispatcher()
            subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.LOADING::class.java
            resumeDispatcher()
            val result = subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.SUCCESS::class.java
        }
    }

    @Test
    fun `verify getList returns error response`() {
        runBlockingTest {
            When calling baseRepository.fetchRepos() itReturns Result.Error_(Exception(""))
            subject.getRepoList()
            pauseDispatcher()
            subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.LOADING::class.java

            resumeDispatcher()
            val result = subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.ERROR::class.java
        }
    }

    @Test
    fun `verify forcedRefresh returns success response`() {
        runBlockingTest {
            When calling baseRepository.fetchRepos() itReturns Result.Success(default)
            subject.forcedRefresh()
            pauseDispatcher()
            subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.LOADING::class.java
            resumeDispatcher()
            val result = subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.SUCCESS::class.java
        }
    }

    @Test
    fun `verify forcedRefresh returns error response`() {
        runBlockingTest {
            When calling baseRepository.fetchRepos() itReturns Result.Error_(Exception(""))
            subject.forcedRefresh()
            pauseDispatcher()
            subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.LOADING::class.java

            resumeDispatcher()
            val result = subject.items.getOrAwaitValue().status shouldBeInstanceOf Status.ERROR::class.java
        }
    }
}
