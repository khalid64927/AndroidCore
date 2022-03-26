package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.utilities.AppViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppViewModelModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(): AppViewModelProvider = AppViewModelProvider()
}
