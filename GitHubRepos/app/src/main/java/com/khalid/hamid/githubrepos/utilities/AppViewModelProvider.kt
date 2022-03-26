package com.khalid.hamid.githubrepos.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import dagger.Module
import kotlin.reflect.KClass

@OpenForTesting
@Module
class AppViewModelProvider {

    // used in  viewModel Delegate
    fun <VM : ViewModel> getViewModel(
        viewModelClass: KClass<VM>,
        storeProducer: () -> ViewModelStore,
        factoryProducer: (() -> ViewModelProvider.Factory)
    ):
            Lazy<VM> = ViewModelLazy(viewModelClass, storeProducer, factoryProducer)

    fun <VM : ViewModel> getViewModel(
        viewModelClass: KClass<VM>,
        storeProducer: ViewModelStoreOwner
    ) =
        ViewModelProvider(storeProducer).get(viewModelClass.java)
}