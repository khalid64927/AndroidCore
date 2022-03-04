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
