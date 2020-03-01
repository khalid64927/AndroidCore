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

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.khalid.hamid.githubrepos.vo.Repositories
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class GitHubServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var gitHubService: GitHubService

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        gitHubService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun getRepos() {
        enqueueResponse("repos-yigit.json")
        val value: Response<List<Repositories>> = runBlocking {
            gitHubService.getRepositories()
        }
        val data: List<Repositories> = value.body() ?: emptyList()
        val size: Int = data.size
        assertThat(size, `is`(24))
        Assert.assertTrue(value.isSuccessful)
        val data2: List<Repositories> = value.body() ?: emptyList()

        val size2: Int = data2.size
        assertThat(size2, `is`(24))

        assertThat(
            data.get(0).author, `is`("ricklamers")
        )
        assertThat(data.get(0).name, `is`("gridstudio"))
        assertThat(data.get(0).avatar, `is`("https://github.com/ricklamers.png"))
        assertThat(data.get(0).language, `is`("JavaScript"))
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val name = "api-response/$fileName"
        val inputStream = javaClass.classLoader?.getResourceAsStream(name)
        val source = inputStream?.let { Okio.source(it) }?.let { Okio.buffer(it) }
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source?.readString(Charsets.UTF_8))
        )
    }
}
