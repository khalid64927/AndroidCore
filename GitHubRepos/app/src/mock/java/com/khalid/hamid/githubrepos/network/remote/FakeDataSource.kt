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

import com.khalid.hamid.githubrepos.network.BaseDataSource
import com.khalid.hamid.githubrepos.network.Result
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
import javax.inject.Inject

class FakeDataSource @Inject constructor() : BaseDataSource {

    override suspend fun fetchRepos(): Result<GitRepos> {
        return Result.Failure(Exception("Fake !"))
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun balance(): Result<BalanceResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun transactions(): Result<TransactionResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun payees(): Result<PayeeResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun transfer(transferRequest: TransferRequest): Result<TransferResponse> {
        TODO("Not yet implemented")
    }
}
