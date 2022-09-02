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

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.khalid.hamid.githubrepos.network.Result.Success
import com.khalid.hamid.githubrepos.utilities.ErrorResponse
import retrofit2.Call
import retrofit2.Response
import timber.log.Timber
import java.net.UnknownHostException

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
    if (this is Success) {
        action(data)
    }
    return this
}

inline fun <T : Any> Result<T>.onError(action: (Throwable) -> Unit): Result<T> {
    if (this is Result.Failure && exception != null) {
        action(exception)
    }
    return this
}
