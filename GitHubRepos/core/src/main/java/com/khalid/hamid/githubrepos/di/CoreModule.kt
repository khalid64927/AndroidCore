package com.khalid.hamid.githubrepos.di

import android.app.Application
import com.khalid.hamid.githubrepos.utilities.AppExecutors
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun providePref(context: Application): Prefs {
        return Prefs(context)
    }

    @Singleton
    @Provides
    fun provideExecutors(): AppExecutors {
        return AppExecutors()
    }
}
