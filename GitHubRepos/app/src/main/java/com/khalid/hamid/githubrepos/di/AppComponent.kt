package com.khalid.hamid.githubrepos.di

import android.app.Application
import com.khalid.hamid.githubrepos.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        RepositoryModule::class,
        ActivityBuilderModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: GithubApp)

}