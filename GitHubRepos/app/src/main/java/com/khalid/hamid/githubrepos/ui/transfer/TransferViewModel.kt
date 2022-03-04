/*
 * MIT License
 *
 * Copyright 2022 Mohammed Khalid Hamid.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without rSingleFragmentActivityestriction, including without limitation the rights
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

package com.khalid.hamid.githubrepos.ui.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import timber.log.Timber

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
            baseRepository.payees().onError {
                Timber.d("onError")
                _transferEventLiveData.value = GetPayeesFailed(it.localizedMessage)
            }.onSuccess {
                Timber.d("onSuccess")
                payeeList = it
                _transferEventLiveData.value = GetPayeesSuccess(it)
            }
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
        val accountNo = payeeList?.data?.find { it.name.equals(payeeSelected, ignoreCase = true) }?.accountNo ?: ""
        launchAsyncAPI {
            baseRepository.transfer(TransferRequest(accountNo, amount.toInt(), description))
                .onError {
                Timber.d("onError")
                _transferEventLiveData.value = TransferFailed(it.localizedMessage)
            }.onSuccess {
                Timber.d("onSuccess")
                _transferEventLiveData.value = TransferSuccess(it)
            }
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
