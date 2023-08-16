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

package com.khalid.hamid.githubrepos.ui.transfer

import com.khalid.hamid.githubrepos.utils.BaseUnitTest

class TransferViewModelTest : BaseUnitTest() {

    /*lateinit var subject: TransferViewModel

    @MockK
    lateinit var baseRepository: BaseRepository

    @MockK
    lateinit var perf: Prefs

    override fun before() {
        super.before()
        val data = Data("111", "", "test")
        val payees = PayeeResponse(listOf(data), "success")
        runBlockingTest {
            coEvery { baseRepository.payees() } returns Result.Success(payees)
        }
        subject = TransferViewModel(baseRepository, perf)
    }

    @Test
    fun `verify InvalidPayee`() = runBlockingTest {
        // Given
        val loginReq = TransferRequest("123", 22, "test")
        val loginResponse: TransferResponse = mockk()
        subject.payeeList = null
        coEvery { baseRepository.transfer(loginReq) } returns Result.Success(loginResponse)
        // When
        subject.transfer("test", "22", "test")

        // Then
        val event = subject.transferEventLiveData.value
        Assert.assertTrue(event is InvalidPayee)
    }

    @Test
    fun `verify transfer success`() = runBlockingTest {
        // Given
        val loginReq = TransferRequest("111", 22, "test")
        val loginResponse: TransferResponse = mockk()
        coEvery { baseRepository.transfer(loginReq) } returns Result.Success(loginResponse)

        // When
        subject.transfer("test", "22", "test")

        // Then
        val event = subject.transferEventLiveData.value
        Assert.assertTrue(event is TransferSuccess)
    }

    @Test
    fun `verify transfer failed`() = runBlockingTest {
        // Given
        val loginReq = TransferRequest("111", 22, "test")
        val loginResponse: TransferResponse = mockk()
        coEvery { baseRepository.transfer(loginReq) } returns Result.Failure(Exception(""))

        // When
        subject.transfer("test", "22", "test")

        // Then
        val event = subject.transferEventLiveData.value
        Assert.assertTrue(event is TransferFailed)
    }*/
}
