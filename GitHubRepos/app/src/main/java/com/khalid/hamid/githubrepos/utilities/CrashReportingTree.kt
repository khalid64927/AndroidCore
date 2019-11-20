package com.khalid.hamid.githubrepos.utilities

import android.util.Log
import timber.log.Timber

class CrashReportingTree : Timber.DebugTree() {

    override fun log(priority: Int, message: String?, vararg args: Any?) {
        super.log(priority, message, *args)

        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return;
        }


    }


}