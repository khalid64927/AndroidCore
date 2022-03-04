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

package com.khalid.hamid.githubrepos.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val PREFS_FILENAME = "com.khalid.hamid.prefs"
    private val CACHED_TIME = "cached_time_in_milliseconds"
    private val ACCESS_TOKEN = "jwt_token"
    private val ACCOUNT_NUMBER = "account_number"
    private val USERNAME = "username"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var cachedTime: String
        get() = prefs.getString(CACHED_TIME, "") ?: ""
        @SuppressLint("NewApi")
        set(value) = prefs.edit().putString(CACHED_TIME, value).apply()

    var accessToken: String
        get() = prefs.getString(ACCESS_TOKEN, "") ?: ""
        @SuppressLint("NewApi")
        set(value) = prefs.edit().putString(ACCESS_TOKEN, value).apply()

    var accountNumber: String
        get() = prefs.getString(ACCOUNT_NUMBER, "") ?: ""
        @SuppressLint("NewApi")
        set(value) = prefs.edit().putString(ACCOUNT_NUMBER, value).apply()

    var userName: String
        get() = prefs.getString(USERNAME, "") ?: ""
        @SuppressLint("NewApi")
        set(value) = prefs.edit().putString(USERNAME, value).apply()

    fun clearAllValues() {
        val editor = prefs.edit().clear().apply()
    }
}
