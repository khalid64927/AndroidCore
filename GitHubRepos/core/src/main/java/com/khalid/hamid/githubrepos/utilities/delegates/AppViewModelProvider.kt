/*
 * Copyright 2022 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.khalid.hamid.githubrepos.utilities.delegates

import androidx.annotation.OpenForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass

@OpenForTesting
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
