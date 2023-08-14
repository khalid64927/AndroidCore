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

package com.khalid.hamid.githubrepos.network

import android.telecom.Call
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductCategoriesList
import com.khalid.hamid.githubrepos.ui.timeline.dto.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface GitHubService {
    @GET
    suspend fun fetchProductCategories(@Url url: String) : Response<ProductCategoriesList>

    @GET
    suspend fun fetchProductForCategory(@Url url: String) : Response<ProductList>

}


object Endpoints {
    const val BASE_URL = "https://s3-ap-northeast-1.amazonaws.com/m-et/Android/json/"
    private const val EXT = ".json"
    const val TIMELINE_CATEGORIES = "${BASE_URL}master${EXT}"

}
