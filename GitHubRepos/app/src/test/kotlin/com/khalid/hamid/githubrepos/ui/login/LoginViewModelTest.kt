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

package com.khalid.hamid.githubrepos.ui.login

import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.Result
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.utils.BaseUnitTest
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.Exception

class LoginViewModelTest : BaseUnitTest() {

    /*lateinit var subject: LoginViewModel

    @MockK
    lateinit var baseRepository: BaseRepository

    @MockK(relaxed = true)
    lateinit var perf: Prefs

    override fun before() {
        super.before()
        subject = LoginViewModel(baseRepository, perf)
    }

    @Test
    fun `verify login success`() = runBlockingTest {
        // Given
        val loginReq = LoginRequest("test", "asdasd")
        val loginResponse: LoginResponse = mockk(relaxed = true)
        coEvery { baseRepository.login(loginReq) } returns Result.Success(loginResponse)

        // When
        subject.login("test", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        assertTrue(event is LoginSuccess)
    }

    @Test
    fun `verify login failure`() = runBlockingTest {
        // Given
        val loginReq = LoginRequest("test", "asdasd")
        coEvery { baseRepository.login(loginReq) } returns Result.Failure(Exception(""))

        // When
        subject.login("test", "asdasd")

        // Then
        val event = subject.registerEventLiveData.value
        assertTrue(event is LoginFailed)
    }*/
}
