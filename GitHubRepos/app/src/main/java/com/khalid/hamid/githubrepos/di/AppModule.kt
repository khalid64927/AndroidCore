package com.khalid.hamid.githubrepos.di

import android.app.Application
import androidx.room.Room
import com.khalid.hamid.githubrepos.db.GithubDb
import com.khalid.hamid.githubrepos.db.RepoDao
import com.khalid.hamid.githubrepos.network.GitHubService
import com.khalid.hamid.githubrepos.utilities.AppExecutors
import com.khalid.hamid.githubrepos.utilities.Constants
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {
    @Singleton
    @Provides
    fun provideGithubService(): GitHubService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): GithubDb {
        return Room
            .databaseBuilder(app, GithubDb::class.java, Constants.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRepoDao(db: GithubDb): RepoDao {
        return db.getRepoDao()
    }

    @Singleton
    @Provides
    fun provideDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Singleton
    @Provides
    fun providePref(context : Application): Prefs {
        return Prefs(context)
    }

    @Singleton
    @Provides
    fun provideExecutors(): AppExecutors {
        return AppExecutors()
    }

}

