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
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitHubService: GitHubService
) {

    suspend fun fetchRepos(): Result<GitRepos> {
        Timber.d("fetchRespos")
        return Result.Failure(Exception(""))
    }

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return gitHubService.register(registerRequest).getRetrofitResult { it }
    }

    suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return gitHubService.login(loginRequest).getRetrofitResult { it }
    }

    suspend fun balance(): Result<BalanceResponse> {
        return gitHubService.balance().getRetrofitResult { it }
    }

    suspend fun transactions(): Result<TransactionResponse> {
        return gitHubService.transactions().getRetrofitResult { it }
    }

    suspend fun payees(): Result<PayeeResponse> {
        return gitHubService.payees().getRetrofitResult { it }
    }

    suspend fun transfer(transferRequest: TransferRequest): Result<TransferResponse> {
        return gitHubService.transfer(transferRequest).getRetrofitResult { it }
    }
}
