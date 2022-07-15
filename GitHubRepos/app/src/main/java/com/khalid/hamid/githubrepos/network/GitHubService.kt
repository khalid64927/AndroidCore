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

import com.khalid.hamid.githubrepos.ui.balance.BalanceResponse
import com.khalid.hamid.githubrepos.ui.balance.TransactionResponse
import com.khalid.hamid.githubrepos.ui.login.LoginRequest
import com.khalid.hamid.githubrepos.ui.login.LoginResponse
import com.khalid.hamid.githubrepos.ui.register.RegisterRequest
import com.khalid.hamid.githubrepos.ui.register.RegisterResponse
import com.khalid.hamid.githubrepos.ui.transfer.PayeeResponse
import com.khalid.hamid.githubrepos.ui.transfer.TransferRequest
import com.khalid.hamid.githubrepos.ui.transfer.TransferResponse
import com.khalid.hamid.githubrepos.vo.GitRepos
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GitHubService {

    @GET("repositories")
    suspend fun fetchRepos(): Response<GitRepos>

    @POST("register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<RegisterResponse>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("balance")
    suspend fun balance(): Response<BalanceResponse>

    @GET("payees")
    suspend fun payees(): Response<PayeeResponse>

    @GET("transactions")
    suspend fun transactions(): Response<TransactionResponse>

    @POST("transfer")
    suspend fun transfer(@Body transferRequest: TransferRequest): Response<TransferResponse>
}
