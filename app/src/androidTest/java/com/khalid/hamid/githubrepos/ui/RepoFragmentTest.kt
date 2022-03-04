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

import androidx.databinding.DataBindingComponent
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.network.Resource
import com.khalid.hamid.githubrepos.testing.SingleFragmentActivity
import com.khalid.hamid.githubrepos.utilities.*
import com.khalid.hamid.githubrepos.vo.Repositories
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.mock

/**
 * Large End-to-End test for the RepoFragment module.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class RepoFragmentTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)

    private lateinit var repository: Repositories

    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)

    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()

    private lateinit var viewModel: RepoViewModel
    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private val repoListData = MutableLiveData<Resource<List<Repositories>>>()

    private val testFragment = TestUserFragment().apply {
    }

    @Before
    fun init() {
        viewModel = mock(RepoViewModel::class.java)
        Mockito.`when`(viewModel._items).thenReturn(repoListData)
        mockBindingAdapter = mock(FragmentBindingAdapters::class.java)

        testFragment.executors = countingAppExecutors.appExecutors
        testFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        testFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }
        activityRule.activity.setFragment(testFragment)
        activityRule.runOnUiThread {
            testFragment.binding.repoList.itemAnimator = null
        }
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }

    @Test
    fun loading() {
        repoListData.postValue(Resource.loading(null))
        onView(withId(R.id.shimmer_view_container)).check(matches(isDisplayed()))
    }

    @Test
    fun test() {
    }

    @Test
    fun error() {
        repoListData.postValue(Resource.error("", null))
        onView(withId(R.id.error_content)).check(matches(isDisplayed()))
    }

    @Test
    fun success() {
        repoListData.postValue(Resource.success(emptyList()))
        onView(withId(R.id.content_success)).check(matches(isDisplayed()))
    }

    class TestUserFragment : RepoFragment() {
        val navController = Mockito.mock(NavController::class.java)
        override fun navController() = navController
    }
}
