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
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Test
import java.lang.Exception

class BalanceViewModelTest : BaseUnitTest() {

    lateinit var subject: BalanceViewModel

    @MockK
    lateinit var baseRepository: BaseRepository

    @MockK
    lateinit var perf: Prefs

    @Test
    fun `verify transactions are displayed`() = runBlockingTest {
        val response = mockk<BalanceResponse>()
        coEvery { baseRepository.balance() } returns Result.Success(response)
        subject = BalanceViewModel(baseRepository, perf)
        assert((subject.balanceEventLiveData.value is BalanceAvailable))
    }

    @Test
    fun `verify transactions are not displayed`() = runBlockingTest {
        coEvery { baseRepository.balance() } returns Result.Failure(Exception(""))
        subject = BalanceViewModel(baseRepository, perf)
        assert((subject.balanceEventLiveData.value is BalanceNotAvailable))
    }
}
