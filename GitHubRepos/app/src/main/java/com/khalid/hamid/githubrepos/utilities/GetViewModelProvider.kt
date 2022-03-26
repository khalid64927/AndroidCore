package com.khalid.hamid.githubrepos.utilities

import androidx.lifecycle.ViewModel

interface GetViewModelProvider {
    fun getViewModelFactory(): AppViewModelProvider
    fun <T : ViewModel> viewModelCreated(viewModel: T)
}