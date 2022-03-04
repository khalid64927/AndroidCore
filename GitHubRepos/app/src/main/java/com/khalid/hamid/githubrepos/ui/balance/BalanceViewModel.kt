/*
 * MIT License
 *
 * Copyright 2022 Mohammed Khalid Hamid.
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
import javax.inject.Inject
import timber.log.Timber

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
            baseRepository.balance()
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
                }
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
