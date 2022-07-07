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

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TransactionResponse(
    @SerializedName("data")
    val data: List<Data> = listOf(),
    @SerializedName("status")
    val status: String = ""
)

data class Receipient(
    @SerializedName("accountHolder")
    val accountHolder: String = "",
    @SerializedName("accountNo")
    val accountNo: String = ""
)

data class Data(
    @SerializedName("amount")
    val amount: Double = 0.0,
    @SerializedName("description")
    val description: String = "",
    @SerializedName("receipient")
    val receipient: Receipient = Receipient(),
    @SerializedName("transactionDate")
    val transactionDate: String = "",
    @SerializedName("transactionId")
    val transactionId: String = "",
    @SerializedName("transactionType")
    val transactionType: String = ""
) {
    // 2022-02-13T14:53:01.630Z
    @SuppressLint("NewApi")
    private val TRANSACTION_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @SuppressLint("NewApi")
    private val TRANSACTION_DATE_FORMAT = DateTimeFormatter.ofPattern("mm MMM yyy")

    @SuppressLint("NewApi")
    fun getDateTime(): String {
        var requiredDateTimeString = ""
        try {
            val localDate = LocalDateTime.parse(transactionDate, TRANSACTION_TIMESTAMP_FORMAT)
            requiredDateTimeString = toTimeSlotString(localDate)
        } catch (e: Exception) {
            return "No Date Available"
        }
        return requiredDateTimeString
    }

    @SuppressLint("NewApi")
    private fun toTimeSlotString(value: LocalDateTime): String {
        return value.format(TRANSACTION_DATE_FORMAT).toString()
    }
}
