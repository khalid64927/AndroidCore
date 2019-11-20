package com.khalid.hamid.githubrepos.utilities

import android.content.Context
import android.content.SharedPreferences

class Prefs (context: Context) {
    val PREFS_FILENAME = "com.khalid.hamid.prefs"
    val CACHED_TIME = "cached_time_in_milliseconds"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var cachedTime: String
        get() = prefs?.getString(CACHED_TIME, "") ?: ""
        set(value) = prefs.edit().putString(CACHED_TIME, value).apply()

}