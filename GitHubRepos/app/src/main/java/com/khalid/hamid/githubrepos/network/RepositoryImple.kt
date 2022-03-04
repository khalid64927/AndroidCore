/*
 * MIT License
 *
 * Copyright 2021 Mohammed Khalid Hamid.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
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
import javax.inject.Inject
import timber.log.Timber

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
