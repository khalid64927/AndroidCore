/*
 * MIT License
 *
 * Copyright 2022 Mohammed Khalid Hamid.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.khalid.hamid.githubrepos.ui.transfer

import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.utilities.Prefs
import java.lang.Exception
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TransferViewModelTest : BaseUnitTest() {

    lateinit var subject: TransferViewModel

    @Mock
    lateinit var baseRepository: BaseRepository

    @Mock
    lateinit var perf: Prefs

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        val data = Data("111", "", "test")
        val payees = PayeeResponse(listOf(data), "success")
        runBlockingTest {
            Mockito.`when`(baseRepository.payees()).thenReturn(Result.Success(payees))
        }
        subject = TransferViewModel(baseRepository, perf)
    }

    @Test
    fun `verify InvalidPayee`() = runBlockingTest {
        // Given
        val loginReq = TransferRequest("123", 22, "test")
        val loginResponse: TransferResponse = Mockito.mock(TransferResponse::class.java)
        Mockito.`when`(baseRepository.transfer(loginReq)).thenReturn(Result.Success(loginResponse))

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
        val loginResponse: TransferResponse = Mockito.mock(TransferResponse::class.java)
        Mockito.`when`(baseRepository.transfer(loginReq)).thenReturn(Result.Success(loginResponse))

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
        val loginResponse: TransferResponse = Mockito.mock(TransferResponse::class.java)
        Mockito.`when`(baseRepository.transfer(loginReq)).thenReturn(Result.Failure(Exception("")))

        // When
        subject.transfer("test", "22", "test")

        // Then
        val event = subject.transferEventLiveData.value
        Assert.assertTrue(event is TransferFailed)
    }
}
