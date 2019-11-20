package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.RepositoryImple
import com.khalid.hamid.githubrepos.network.local.LocalDataSource
import com.khalid.hamid.githubrepos.network.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): BaseRepository {
        return BaseRepository(RepositoryImple(localDataSource,remoteDataSource))
    }
}