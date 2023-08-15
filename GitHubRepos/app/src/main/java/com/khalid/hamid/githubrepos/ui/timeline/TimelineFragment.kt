package com.khalid.hamid.githubrepos.ui.timeline

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentTimelineBinding
import com.khalid.hamid.githubrepos.utilities.toolbar.ToolbarWithUp
import com.khalid.hamid.githubrepos.utilities.delegates.fragmentViewLifecycleDelegate
import com.khalid.hamid.githubrepos.utilities.toolbar.DefaultToolbar
import com.khalid.hamid.githubrepos.utilities.toolbar.withLogo
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
            when(it){
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
        activity?.run {
            val pagerAdapter = PagerAdapter(this, receivedProducts)
            v.timelinePager.adapter = pagerAdapter
            TabLayoutMediator(v.tablayout, v.timelinePager) { tab, position -> }.attach()
            v.timelinePager.setCurrentItem(0, true)
        }
    }
}

interface TimelineFragmentActions {
    // Define user action here

}