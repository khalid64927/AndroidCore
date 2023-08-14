package com.khalid.hamid.githubrepos.ui.timeline

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentTimelineBinding
import com.khalid.hamid.githubrepos.utilities.ToolbarWithUp
import com.khalid.hamid.githubrepos.utilities.fragmentViewLifecycleDelegate
import com.khalid.hamid.githubrepos.utilities.withLogo
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class TimelineFragment : BaseFragment(), TimelineFragmentActions {

    override val mLayout = R.layout.fragment_timeline

    override val toolbarConfig = ToolbarWithUp().withLogo()

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
            when(it){
                is ReceivedProducts -> {
                    Timber.d("ReceivedProducts Event ")
                    buildViewPager()

                }
                is FailedToFetchProducts -> {
                    Timber.d("FailedToFetchProducts Event ")

                }
            }
        }
    }

    private fun buildViewPager() {
        activity?.run {
            val pagerAdapter = PagerAdapter(this)
            v.timelinePager.adapter = pagerAdapter
            TabLayoutMediator(v.tablayout, v.timelinePager) { tab, position -> }.attach()
            v.timelinePager.setCurrentItem(1, false)
        }

    }
}

interface TimelineFragmentActions {
    // Define user action here

}