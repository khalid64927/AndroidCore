/*
 * Copyright 2021 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.utils

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.mockito.MockitoAnnotations

open class BaseUnitTest : TestContract {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Tell JUnit to force tests to be executed synchronously
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    /**
     * TODO: migrate TestCoroutineScope
     *  fun runBlockingTest(block: suspend TestScope.() -> Unit) =
     *   mainCoroutineRule.scope.runTest { block() }
     *
     *   Convenience method to call runBlockingTest with mainCoroutineRule's scope
     *   */

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        mainCoroutineRule.runBlockingTest(block)

    override var mockWebServer = MockWebServer()

    @CallSuper
    @Before
    override fun before() {
        MockKAnnotations.init(this)
        MockitoAnnotations.openMocks(this)
        mockWebServer.run {
            start()
        }
    }

    override fun after() {
        mockWebServer.run {
            shutdown()
        }
    }
}
