/*
 * Copyright 2022 Mohammed Khalid Hamid.
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

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.khalid.hamid.githubrepos.utilities.readTextAndClose
import com.khalid.hamid.githubrepos.vo.Repositories
import org.junit.Assert.*
import org.junit.Test

class FakeDataSourceTest {

    data class AllRepos(private val repos: List<Repositories>)

    @Test
    fun getRepositories() {
        val typeToken = object : TypeToken<List<Repositories>>() {}.type
        val jsonPath = "api-response/repos-khalid.json"
        var mockJson = javaClass.classLoader?.getResourceAsStream(jsonPath)
            ?.readTextAndClose()
        val data = Gson().fromJson<List<Repositories>>(mockJson, typeToken)
        assert(data != null)
    }

    @Test
    fun fetchRepos() {
        val typeToken = object : TypeToken<List<Repositories>>() {}.type
        val jsonPath = "api-response/repos-khalid.json"
        var mockJson = javaClass.classLoader?.getResourceAsStream(jsonPath)
            ?.readTextAndClose()
        val data = Gson().fromJson<List<Repositories>>(mockJson, typeToken)
        assert(data != null)
    }
}
