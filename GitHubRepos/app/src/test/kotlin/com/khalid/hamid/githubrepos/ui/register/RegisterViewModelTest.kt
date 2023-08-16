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
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import java.lang.Exception

class RegisterViewModelTest : BaseUnitTest() {

    /*lateinit var subject: RegisterViewModel

    @MockK
    lateinit var baseRepository: BaseRepository

    @MockK(relaxed = true)
    lateinit var perf: Prefs

    override fun before() {
        super.before()
        every { perf.accessToken } returns ""
        every { perf.accountNumber } returns ""
        every { perf.clearAllValues() } returns any()
        subject = RegisterViewModel(baseRepository, perf)
    }

    @Test
    fun `verify user creation success`() = runBlockingTest {
        // Given
        val req = RegisterRequest("test", "asdasd")
        val res: RegisterResponse = mockk()
        every { res.token } returns ""
        coEvery { baseRepository.register(req) } returns Result.Success(res)

        // When
        subject.register("test", "asdasd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is Registered)
    }

    @Test
    fun `verify invalid username`() = runBlockingTest {
        // Given
        val req = RegisterRequest("tes", "asdasd")
        val res: RegisterResponse = mockk()
        coEvery { baseRepository.register(req) } returns Result.Success(res)

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
        val res: RegisterResponse = mockk()
        coEvery { baseRepository.register(req) } returns Result.Success(res)

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
        val res: RegisterResponse = mockk()
        coEvery { baseRepository.register(req) } returns Result.Success(res)

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
        val res: RegisterResponse = mockk()
        coEvery { baseRepository.register(req) } returns Result.Success(res)

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
        val res: RegisterResponse = mockk()
        coEvery { baseRepository.register(req) } returns Result.Failure(Exception(""))

        // When
        subject.register("test", "asdasd", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        Assert.assertTrue(event is RegisterFailed)
    }*/
}
