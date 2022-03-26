package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.utilities.AppViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppViewModelModule {

    @Singleton
    @Provides
    fun provideViewModelFactory(): AppViewModelProvider = AppViewModelProvider()
}
