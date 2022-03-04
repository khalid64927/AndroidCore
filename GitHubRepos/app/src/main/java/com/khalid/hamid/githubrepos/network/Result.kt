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

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.utilities.ErrorResponse
import java.net.UnknownHostException
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }

    companion object {
        val MSG_GENERIC_ERROR = "Something went wrong, please try again."
        val MSG_UNABLE_TO_CONNECT =
            "Unable to connect, please check your internet connection and try again."
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Success && data != null

fun getErrorMsgFor(e: Throwable?): String? {
    return when (e) {
        is UnknownHostException -> Result.MSG_UNABLE_TO_CONNECT
        else -> Result.MSG_GENERIC_ERROR
    }
}
fun <T : Any, E : Any> Call<T>.getRetrofitResult(converter: (T) -> E): Result<E> {
    val response: Response<T>
    try {
        response = execute()
    } catch (e: Throwable) {
        Timber.e("exception ", e)
        return Result.Failure(java.lang.Exception(getErrorMsgFor(e)))
    }

    return if (response.isSuccessful) {
        try {
            val data = converter(response.body() as T)
            Success(data)
        } catch (e: java.lang.Exception) {
            Result.Failure(java.lang.Exception(getErrorMsgFor(e)))
        }
    } else {
        Result.Failure(java.lang.Exception(Result.MSG_GENERIC_ERROR))
    }
}

fun <T : Any, E : Any> Response<T>.getRetrofitResult(converter: (T) -> E): Result<E> {
    return if (isSuccessful) {
        try {
            val data = converter(body() as T)
            Success(data)
        } catch (e: java.lang.Exception) {
            Result.Failure(java.lang.Exception(getErrorMsgFor(e)))
        }
    } else {
        var data = errorBody()?.string()
        if (data != null) {
            var errorResponse: ErrorResponse? = null
            try {
                errorResponse = Gson().fromJson(data, ErrorResponse::class.java)
                Result.Failure(java.lang.Exception(errorResponse.errorMessage))
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                Result.Failure(java.lang.Exception(Result.MSG_GENERIC_ERROR))
            }
        } else {
            Result.Failure(java.lang.Exception(Result.MSG_GENERIC_ERROR))
        }
    }
}

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success)
        action(data)
    return this
}

inline fun <T : Any> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    if (this is Result.Failure && exception != null)
        action(exception)
    return this
}
