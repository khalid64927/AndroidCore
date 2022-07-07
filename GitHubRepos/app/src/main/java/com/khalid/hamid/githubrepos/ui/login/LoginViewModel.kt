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

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class LoginViewModel @Inject constructor(
    val baseRepository: BaseRepository,
    val perf: Prefs
) : BaseViewModel() {

    init {
        perf.clearAllValues()
    }

    val registerEventLiveData: LiveData<RegisterEvent>
        get() = _registerEventLiveData
    private val _registerEventLiveData = MutableLiveData<RegisterEvent>()

    private fun checkUserName(username: String): Boolean {
        if (username.isBlank()) {
            return false
        }

        val regex = "^[a-zA-Z0-9._-]{4,15}\$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(username)
        return m.matches()
    }

    private fun checkPwd(pwd: String): Boolean {
        if (pwd.isBlank()) {
            return false
        }

        val regex = "^[a-zA-Z]\\w{4,14}$"
        val p: Pattern = Pattern.compile(regex)
        val m: Matcher = p.matcher(pwd)
        return m.matches()
    }

    fun login(username: String, pwd: String): Boolean {
        if (!checkUserName(username)) {
            _registerEventLiveData.value = InvalidUserName("")
            return false
        }
        if (!checkPwd(pwd)) {
            _registerEventLiveData.value = InvalidPwd("")
            return false
        }

        launchAsyncAPI {
            baseRepository.login(LoginRequest(username, pwd))
                .onError {
                    _registerEventLiveData.value = LoginFailed(it.localizedMessage)
                    showError(it.localizedMessage)
                    perf.clearAllValues()
                }.onSuccess {
                    Timber.d("onSuccess ${perf.accessToken}")
                    // get balance
                    perf.accessToken = it.token
                    Timber.d("token ${perf.accessToken}")
                    perf.accountNumber = it.accountNo
                    perf.userName = it.username
                    _registerEventLiveData.value = LoginSuccess("")
                }
            Timber.d("five")
        }
        return true
    }
}

sealed class RegisterEvent
class InvalidPwd(val msg: String) : RegisterEvent()
class InvalidUserName(val msg: String) : RegisterEvent()
class LoginFailed(val msg: String) : RegisterEvent()
class LoginSuccess(val msg: String) : RegisterEvent()
class Default(val msg: String) : RegisterEvent()

data class LoginResponse(
    @SerializedName("accountNo")
    val accountNo: String = "",
    @SerializedName("status")
    val status: String = "",
    @SerializedName("token")
    val token: String = "",
    @SerializedName("username")
    val username: String = ""
)

data class LoginRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
