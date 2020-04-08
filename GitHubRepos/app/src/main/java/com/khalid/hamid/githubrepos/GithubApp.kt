/*
 * Copyright 2020 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos

import android.app.Activity
import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import com.khalid.hamid.githubrepos.di.AppInjector
import com.khalid.hamid.githubrepos.utilities.CrashReportingTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class GithubApp : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
