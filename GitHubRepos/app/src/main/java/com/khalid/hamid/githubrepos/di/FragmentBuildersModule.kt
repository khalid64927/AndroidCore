package com.khalid.hamid.githubrepos.di

import com.khalid.hamid.githubrepos.ui.RepoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRepoFragment(): RepoFragment

}