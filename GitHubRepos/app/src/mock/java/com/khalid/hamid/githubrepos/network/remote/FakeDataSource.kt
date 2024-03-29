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

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.vo.Repositories
import javax.inject.Inject

class FakeDataSource @Inject constructor(private val repoDao: RepoDao, private val appContext: Application) : BaseDataSource {

    override suspend fun getRepositories(): Result<List<Repositories>> {
        val typeToken = object : TypeToken<List<Repositories>>() {}.type
        val jsonPath = "api-response/repos-khalid.json"
        val mockJson = appContext.assets.open(jsonPath).bufferedReader().use {
            it.readText()
        }
        val data = Gson().fromJson<List<Repositories>>(mockJson, typeToken)
        return Result.Success(data)
    }

    override suspend fun fetchRepos(): Result<List<Repositories>> {
        val typeToken = object : TypeToken<List<Repositories>>() {}.type
        val jsonPath = "api-response/repos-khalid.json"
        val mockJson = appContext.assets.open(jsonPath).bufferedReader().use {
            it.readText()
        }
        val data = Gson().fromJson<List<Repositories>>(mockJson, typeToken)
        return Result.Success(data)
    }
}
