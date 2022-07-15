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

import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import com.khalid.hamid.githubrepos.ui.balance.BalanceResponse
import com.khalid.hamid.githubrepos.ui.balance.TransactionResponse
import com.khalid.hamid.githubrepos.ui.login.LoginRequest
import com.khalid.hamid.githubrepos.ui.login.LoginResponse
import com.khalid.hamid.githubrepos.ui.register.RegisterRequest
import com.khalid.hamid.githubrepos.ui.register.RegisterResponse
import com.khalid.hamid.githubrepos.ui.transfer.PayeeResponse
import com.khalid.hamid.githubrepos.ui.transfer.TransferRequest
import com.khalid.hamid.githubrepos.ui.transfer.TransferResponse
import com.khalid.hamid.githubrepos.utilities.EspressoIdlingResource
import com.khalid.hamid.githubrepos.vo.GitRepos
import timber.log.Timber
import javax.inject.Inject

/**
 * This will return data from either DB or get from network
*/
class RepositoryImple @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseDataSource {

    override suspend fun fetchRepos(): Result<GitRepos> {
        val fetchedData = remoteDataSource.fetchRepos()
        Timber.d("fetched data ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
        when (fetchedData) {
            is Success -> {
                Timber.d("Success ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                return fetchedData
            }

            else -> {
                Timber.d("Error ${EspressoIdlingResource.countingIdlingResource.getCounterVal()}")
                return fetchedData
            }
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResponse> {
        return remoteDataSource.register(registerRequest)
    }

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResponse> {
        return remoteDataSource.login(loginRequest)
    }

    override suspend fun balance(): Result<BalanceResponse> {
        return remoteDataSource.balance()
    }

    override suspend fun transactions(): Result<TransactionResponse> {
        return remoteDataSource.transactions()
    }

    override suspend fun payees(): Result<PayeeResponse> {
        return remoteDataSource.payees()
    }

    override suspend fun transfer(transferRequest: TransferRequest): Result<TransferResponse> {
        return remoteDataSource.transfer(transferRequest)
    }
}
