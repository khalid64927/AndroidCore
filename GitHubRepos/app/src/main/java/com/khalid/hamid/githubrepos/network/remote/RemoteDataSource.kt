/*
 * Copyright 2021 Mohammed Khalid Hamid.
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
import com.khalid.hamid.githubrepos.network.getRetrofitResult
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import com.khalid.hamid.githubrepos.vo.GitRepos
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubService: GitHubService
) {

    suspend fun fetchProductCategories(url: String): Result<ProductCategoriesList> {
        return gitHubService.fetchProductCategories(url).getRetrofitResult { it }
    }

    suspend fun fetchProductForCategory(url: String): Result<ProductList> {
        return gitHubService.fetchProductForCategory(url).getRetrofitResult { it }
    }


}
