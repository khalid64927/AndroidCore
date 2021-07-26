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
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.databinding.FragmentRepoBinding
import com.khalid.hamid.githubrepos.di.Injectable
import com.khalid.hamid.githubrepos.network.Status
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.*
import com.khalid.hamid.githubrepos.vo.toViewData
import javax.inject.Inject
import timber.log.Timber

@OpenForTesting
class RepoFragment : Fragment(), Injectable {

    @Inject
    lateinit var executors: AppExecutors
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var app: Application
    private var adapter by autoCleared<RepoAdapter>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<FragmentRepoBinding>()
    val repoViewModel: RepoViewModel by viewModels {
        viewModelFactory
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repoLiveData = repoViewModel.items
        val decorator = SimpleDividerItemDecoration(app)
        val adapter = RepoAdapter(dataBindingComponent, executors) { repositories -> }
        this.adapter = adapter
        binding.repoList.adapter = this.adapter
        binding.repoList.addItemDecoration(decorator)
        binding.callback = object : RetryListener {

            override fun fetchFromRepote() {
                repoViewModel.getRepoList()
            }
        }
        binding.forceRefresh = object : ForceRefresh {
            override fun refresh() {
                repoViewModel.forcedRefresh()
            }
        }
        repoLiveData.observe(viewLifecycleOwner, Observer { gitRepos ->
            binding.resource = gitRepos
            this.adapter.submitList(gitRepos.data?.values?.map { it.toViewData() })
            when (gitRepos.status) {
                Status.SUCCESS -> Timber.d("success %s", gitRepos.data.toString())
                Status.ERROR -> Timber.d("error ")
                Status.LOADING -> Timber.d("loading ")
            }
        })
        repoViewModel.getRepoList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val dataBinding = DataBindingUtil.inflate<FragmentRepoBinding>(
            inflater,
            R.layout.fragment_repo,
            container,
            false
        )
        Timber.d("onCreateView")
        setHasOptionsMenu(true)
        binding = dataBinding
        return binding.repoRoot
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
