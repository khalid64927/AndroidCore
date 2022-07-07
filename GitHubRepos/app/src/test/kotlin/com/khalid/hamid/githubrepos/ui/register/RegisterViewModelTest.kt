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

package com.khalid.hamid.githubrepos.ui.register

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.Exception

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
