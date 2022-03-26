package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.utilities.DispatcherProvider
import com.khalid.hamid.githubrepos.utilities.DispatcherProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoroutinesModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProviderImpl()
}
