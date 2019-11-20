package com.khalid.hamid.githubrepos.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khalid.hamid.githubrepos.ViewModelFactory
import com.khalid.hamid.githubrepos.ui.RepoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(RepoViewModel::class)
    abstract fun bindRepoViewModel(repoViewModel: RepoViewModel): ViewModel
}
