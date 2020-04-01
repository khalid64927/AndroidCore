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

package com.khalid.hamid.githubrepos.ui

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.ViewModelFragment
import com.khalid.hamid.githubrepos.databinding.FragmentRepoBinding
import com.khalid.hamid.githubrepos.di.Injectable
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.AppExecutors
import com.khalid.hamid.githubrepos.utilities.ForceRefresh
import com.khalid.hamid.githubrepos.utilities.RetryListener
import com.khalid.hamid.githubrepos.utilities.autoCleared
import javax.inject.Inject
import timber.log.Timber

@OpenForTesting
open class RepoFragment : ViewModelFragment<RepoViewModel, FragmentRepoBinding>(), Injectable {

    @Inject
    lateinit var executors: AppExecutors

    private var adapterD by autoCleared<RepoAdapter>()
    private lateinit var repoDataBinding: FragmentRepoBinding

    @Inject
    lateinit var app: Application

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoViewModel::class.java)
        val repoLD = viewModel._items

        val decorator = SimpleDividerItemDecoration(app)

        val adapter = RepoAdapter(dataBindingComponent, executors) { repositories ->
            //
        }

        adapterD = adapter
        binding.repoList.adapter = adapterD
        binding.repoList.addItemDecoration(decorator)

        binding.callback = object : RetryListener {
            override fun fetchFromRepote() {
                viewModel.getRepoList()
            }
        }

        binding.forceRefresh = object : ForceRefresh {
            override fun refresh() {
                viewModel.forcedRefresh()
            }
        }

        repoLD.observe(this, Observer { repositories ->
            // binding.repoList = repositories.data
            binding.resource = repositories
                Timber.d("got " + repositories.data.toString())

            if (repositories.status == Status.SUCCESS) {
                Timber.d("success ")
            } else if (repositories.status == Status.ERROR) {
                Timber.d("ERROR ")
            } else if (repositories.status == Status.LOADING) {
                Timber.d("LOADING ")
            }

            adapterD.submitList(repositories.data)
        })
        viewModel.getRepoList()
    }

    override fun getViewBindings(container: ViewGroup?): FragmentRepoBinding {
        return DataBindingUtil.inflate<FragmentRepoBinding>(layoutInflater, R.layout.fragment_repo, container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        Timber.d("onCreateView")
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {

            R.id.menu_sort_name -> {
                // sort by name
            true
            }
            R.id.menu_sort_star -> {
                // sort adapter by stars
                true
            }
            else -> false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Timber.d("onCreateOptionsMenu")
        inflater.inflate(R.menu.arrange_list, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    /**
     * Created to be able to override in tests
     */
    open fun navController() = findNavController()
}
