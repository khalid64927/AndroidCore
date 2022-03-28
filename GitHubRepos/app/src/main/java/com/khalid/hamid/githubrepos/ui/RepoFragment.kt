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
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentRepoBinding
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@OpenForTesting
@AndroidEntryPoint
class RepoFragment : BaseFragment() {

    @Inject
    lateinit var executors: AppExecutors

    @Inject
    lateinit var app: Application

    override val mLayout = R.layout.fragment_repo

    override fun getPageName(): String = this::class.java.simpleName

    private var adapter by autoCleared<RepoAdapter>()

    private var v by fragmentViewLifecycleDelegate<FragmentRepoBinding>({
        getFragmentBinding()
    })

    // repoViewModel is initialised in BaseFragment's onViewCreated
    private var repoViewModel by fragmentViewLifecycleDelegate<RepoViewModel>({
        getViewModel()
    })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.lifecycleOwner = viewLifecycleOwner
        val repoLiveData = repoViewModel._items
        val decorator = SimpleDividerItemDecoration(app)
        val adapter = RepoAdapter(FragmentDataBindingComponent(this), executors) {
            // no-op
        }
        this.adapter = adapter
        v.repoList.adapter = this.adapter
        v.repoList.addItemDecoration(decorator)
        v.callback = object : RetryListener {
            override fun fetchFromRepo() {
                repoViewModel.getRepoList()
            }
        }
        v.forceRefresh = object : ForceRefresh {
            override fun refresh() {
                repoViewModel.forcedRefresh()
            }
        }
        repoLiveData.observe(
            viewLifecycleOwner
        ) { repositories ->
            v.resource = repositories
            this.adapter.submitList(repositories.data)
            when (repositories.status) {
                Status.SUCCESS -> Timber.d("success %s", repositories.data.toString())
                Status.ERROR -> Timber.d("error ")
                Status.LOADING -> Timber.d("loading ")
            }
        }
        repoViewModel.getRepoList()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.menu_sort_name -> true
            // sort adapter by stars
            R.id.menu_sort_star -> true
            else -> false
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Timber.d("onCreateOptionsMenu")
        inflater.inflate(R.menu.arrange_list, menu)
    }

    /**
     * Created to be able to override in tests
     */
    open fun navController() = findNavController()
}
