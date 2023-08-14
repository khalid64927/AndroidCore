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

package com.khalid.hamid.githubrepos.ui.balance

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class BalanceViewModel @Inject constructor(
    val baseRepository: BaseRepository,
    val perf: Prefs
) : BaseViewModel() {

    val balanceEventLiveData: LiveData<BalanceEvent>
        get() = _balanceEventLiveData
    private val _balanceEventLiveData = MutableLiveData<BalanceEvent>()

    init {
        getBalance()
    }

    private fun getBalance() {
        launchAsyncAPI {
            /*baseRepository.balance()
                .onError {
                    _balanceEventLiveData.value = BalanceNotAvailable(it.localizedMessage)
                    showError(it.localizedMessage)
                }.onSuccess {
                    Timber.d("onSuccess")
                    _balanceEventLiveData.value = BalanceAvailable(it)
                }

            baseRepository.transactions()
                .onError {
                    Timber.d(" transactions onError")
                    showError(it.localizedMessage)
                }
                .onSuccess {
                    Timber.d("transactions onSuccess")
                    val transactionList = it.data.groupBy { it.getDateTime() }
                    _balanceEventLiveData.value = TransactionAvailable(transactionList)
                }*/
        }
    }
}

sealed class BalanceEvent
class BalanceAvailable(val balance: BalanceResponse) : BalanceEvent()
class BalanceNotAvailable(val msg: String) : BalanceEvent()
class TransactionAvailable(val transactionList: Map<String, List<Data>>) : BalanceEvent()
class TransactionNotAvailable(val msg: String) : BalanceEvent()

data class BalanceResponse(
    @SerializedName("accountNo")
    val accountNo: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("balance")
    val balance: String = ""
)

data class BalanceView(
    val balanceResponse: BalanceResponse,
    val transactionResponse: TransactionResponse
)
