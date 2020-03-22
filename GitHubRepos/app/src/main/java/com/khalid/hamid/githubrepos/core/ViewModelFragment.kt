/*
 * Copyright 2020 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingComponent
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.khalid.hamid.githubrepos.utilities.FragmentDataBindingComponent
import com.khalid.hamid.githubrepos.utilities.autoCleared
import javax.inject.Inject

abstract class ViewModelFragment<VM : ViewModel, VB : ViewDataBinding> : Fragment() {
    lateinit var viewModel: VM
    var binding: VB by autoCleared<VB>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBindings(container)
        return binding.root
    }
    abstract fun getViewBindings(container: ViewGroup?): VB

    @CallSuper
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModel::class.java)
    }
}
