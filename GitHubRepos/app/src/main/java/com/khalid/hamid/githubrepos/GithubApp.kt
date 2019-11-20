package com.khalid.hamid.githubrepos

import android.app.Activity
import android.app.Application
import com.khalid.hamid.githubrepos.di.AppInjector
import com.khalid.hamid.githubrepos.utilities.CrashReportingTree
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class GithubApp:Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }else{
            Timber.plant(CrashReportingTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

}