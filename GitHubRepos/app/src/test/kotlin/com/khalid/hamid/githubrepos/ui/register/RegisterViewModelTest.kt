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

package com.khalid.hamid.githubrepos.ui.register

import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.utilities.Prefs
import java.lang.Exception
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RegisterViewModelTest : BaseUnitTest() {
    private lateinit var mockWebServer: MockWebServer

    lateinit var subject: RegisterViewModel

    @Mock
    lateinit var baseRepository: BaseRepository

    @Mock
    lateinit var perf: Prefs

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subject = RegisterViewModel(baseRepository, perf)
    }

    @Test
    fun `verify user creation success`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asdasd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Success(res))

        // When
        subject.register("test", "asdasd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is RegisterSuccess)
    }

    @Test
    fun `verify invalid username`() = runBlockingTest {
        // Given
        val req = RegisterRequest("tes", "asdasd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Success(res))

        // When
        subject.register("tes", "asdasd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is InvalidUserName)
    }

    @Test
    fun `verify invalid pwd`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Success(res))

        // When
        subject.register("tes", "asd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is InvalidUserName)
    }

    @Test
    fun `verify invalid conf pwd`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asdasd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Success(res))

        // When
        subject.register("test", "asdasd", "rrrr")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is InvalidConfirmPwd)
    }

    @Test
    fun `verify mismatch pwd`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asdasd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Success(res))

        // When
        subject.register("test", "asdasd", "asdasddd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is MismatchPwd)
    }

    @Test
    fun `verify register failed`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asdasd")
        val res: RegisterResponse = Mockito.mock(RegisterResponse::class.java)
        Mockito.`when`(baseRepository.register(req)).thenReturn(Result.Failure(Exception("")))

        // When
        subject.register("test", "asdasd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is RegisterFailed)
    }
}
