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

package com.khalid.hamid.githubrepos.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(
    val baseRepository: BaseRepository,
    val perf: Prefs
) : BaseViewModel() {

    init {
        getPayees()
    }

    val transferEventLiveData: LiveData<TransferEvent>
        get() = _transferEventLiveData
    private val _transferEventLiveData = MutableLiveData<TransferEvent>()

    var payeeList: PayeeResponse? = null
    private fun getPayees() {
        launchAsyncAPI {
            /*baseRepository.payees().onError {
                Timber.d("onError")
                _transferEventLiveData.value = GetPayeesFailed(it.localizedMessage)
            }.onSuccess {
                Timber.d("onSuccess")
                payeeList = it
                _transferEventLiveData.value = GetPayeesSuccess(it)
            }*/
        }
    }

    private fun validateDescription(description: String): Boolean {
        if (description.isBlank()) {
            return false
        }

        val regex = "^[a-zA-Z0-9._-]{4,15}\$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(description)
        return m.matches()
    }

    fun transfer(payeeSelected: String, amount: String, description: String) {
        if (payeeList == null) {
            _transferEventLiveData.value = InvalidPayee("")
            return
        }

        try {
            amount.toInt()
        } catch (e: Exception) {
            _transferEventLiveData.value = InvalidAmount("")
        }

        if (!validateDescription(description)) {
            _transferEventLiveData.value = InvalidDescription("")
        }
        val accountNo = payeeList?.data
            ?.find { it.name.equals(payeeSelected, ignoreCase = true) }
            ?.accountNo ?: ""
        launchAsyncAPI {
            /*baseRepository.transfer(TransferRequest(accountNo, amount.toInt(), description))
                .onError {
                    Timber.d("onError")
                    _transferEventLiveData.value = TransferFailed(it.localizedMessage)
                }.onSuccess {
                    Timber.d("onSuccess")
                    _transferEventLiveData.value = TransferSuccess(it)
                }*/
        }
    }
}

sealed class TransferEvent
class InvalidPayee(val msg: String) : TransferEvent()
class InvalidAmount(val msg: String) : TransferEvent()
class InvalidDescription(val msg: String) : TransferEvent()
class TransferFailed(val msg: String) : TransferEvent()
class TransferSuccess(val transferResponse: TransferResponse) : TransferEvent()
class GetPayeesFailed(val msg: String) : TransferEvent()
class GetPayeesSuccess(val payeeResponse: PayeeResponse) : TransferEvent()
class Default(val msg: String) : TransferEvent()

data class TransferResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("transactionId")
    val transactionId: String,
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("recipientAccount")
    val recipientAccount: String
)

data class TransferRequest(
    @SerializedName("receipientAccountNo")
    val receipientAccountNo: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("description")
    val description: String
)
