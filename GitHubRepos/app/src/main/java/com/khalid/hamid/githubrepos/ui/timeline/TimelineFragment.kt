/*
 * Copyright 2023 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.ui.timeline

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentTimelineBinding
import com.khalid.hamid.githubrepos.utilities.delegates.fragmentViewLifecycleDelegate
import com.khalid.hamid.githubrepos.utilities.toolbar.DefaultToolbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TimelineFragment : BaseFragment(), TimelineFragmentActions {

    override val mLayout = R.layout.fragment_timeline

    override val toolbarConfig = DefaultToolbar()

    private var v by fragmentViewLifecycleDelegate(
        { binding as FragmentTimelineBinding }
    )

    private val timelineViewModel: TimelineViewModel by fragmentViewLifecycleDelegate({
        getViewModel()
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        context?.run {
            showAlert(this, "Testing")
        }

        v.action = this
        timelineViewModel.mainTimelineEventLiveData.observe(viewLifecycleOwner) {
            Timber.d("Event ${it.javaClass.canonicalName}")
            when (it) {
                is ReceivedProducts -> {
                    Timber.d("ReceivedProducts Event ")
                    buildViewPager(it)
                }
                is FailedToFetchProducts -> {
                    Timber.d("FailedToFetchProducts Event ")
                }
            }
        }
    }

    private fun buildViewPager(receivedProducts: ReceivedProducts) {
        val tabNameList = receivedProducts.categoriesList.map { it.name }
        activity?.run {
            val pagerAdapter = PagerAdapter(this, receivedProducts.categoriesList)
            v.timelinePager.adapter = pagerAdapter
            TabLayoutMediator(v.tablayout, v.timelinePager) { tab, position -> tab.setText(
                tabNameList[position]
            )}.attach()
            v.timelinePager.setCurrentItem(0, true)
        }
    }

    override fun sellProductClicked() {
        // no-op
    }
}

interface TimelineFragmentActions {
    // Define user action here
    fun sellProductClicked()
}
