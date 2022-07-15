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
