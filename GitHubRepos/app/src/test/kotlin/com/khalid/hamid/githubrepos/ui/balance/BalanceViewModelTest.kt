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

package com.khalid.hamid.githubrepos.ui.balance

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.lang.Exception

class BalanceViewModelTest : BaseUnitTest() {

    lateinit var subject: BalanceViewModel

    @Mock
    lateinit var baseRepository: BaseRepository

    @Mock
    lateinit var perf: Prefs

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `verify transactions are displayed`() = runBlockingTest {
        val response = mock(BalanceResponse::class.java)
        runBlockingTest {
            Mockito.`when`(baseRepository.balance()).thenReturn(Result.Success(response))
        }
        subject = BalanceViewModel(baseRepository, perf)

        assert((subject.balanceEventLiveData.value is BalanceAvailable))
    }

    @Test
    fun `verify transactions are not displayed`() = runBlockingTest {
        runBlockingTest {
            Mockito.`when`(baseRepository.balance()).thenReturn(Result.Failure(Exception("")))
        }
        subject = BalanceViewModel(baseRepository, perf)
        assert((subject.balanceEventLiveData.value is BalanceNotAvailable))
    }
}
