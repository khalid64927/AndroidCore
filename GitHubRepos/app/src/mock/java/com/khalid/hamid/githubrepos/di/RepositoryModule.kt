package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.network.remote.FakeDataSource
import com.khalid.hamid.githubrepos.db.GithubDb
import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.network.BaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(repoDao: RepoDao): BaseRepository {
        return BaseRepository(FakeDataSource(repoDao))
    }
}