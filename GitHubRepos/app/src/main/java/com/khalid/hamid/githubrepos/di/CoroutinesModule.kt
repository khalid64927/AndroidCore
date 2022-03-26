package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.utilities.DispatcherProvider
import com.khalid.hamid.githubrepos.utilities.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CoroutinesModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}
