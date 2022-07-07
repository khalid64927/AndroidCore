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
class RegisterViewModel @Inject constructor(
    val baseRepository: BaseRepository,
    val perf: Prefs
) : BaseViewModel() {

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

    fun register(username: String, pwd: String, confirmPwd: String): Boolean {
        if (!checkUserName(username)) {
            _registerEventLiveData.value = InvalidUserName("")
            return false
        }
        if (!checkPwd(pwd)) {
            _registerEventLiveData.value = InvalidPwd("")
            return false
        }

        if (!checkPwd(confirmPwd)) {
            _registerEventLiveData.value = InvalidConfirmPwd("")
            return false
        }

        if (!pwd.equals(confirmPwd, ignoreCase = false)) {
            _registerEventLiveData.value = MismatchPwd("")
            return false
        }

        launchAsyncAPI {
            baseRepository.register(RegisterRequest(username, pwd))
                .onError {
                    _registerEventLiveData.value = RegisterFailed(it.localizedMessage)
                    showError(it.localizedMessage)
                }.onSuccess {
                    Timber.d("onSuccess")
                    perf.accessToken = it.token
                    Timber.d("toke ${perf.accessToken}")
                    _registerEventLiveData.value = Registered("")
                }
        }
        return true
    }
}

sealed class RegisterEvent
class IncorrectConfirmPwd(val msg: String) : RegisterEvent()
class InvalidPwd(val msg: String) : RegisterEvent()
class InvalidConfirmPwd(val msg: String) : RegisterEvent()
class MismatchPwd(val msg: String) : RegisterEvent()
class InvalidUserName(val msg: String) : RegisterEvent()
class Registered(val msg: String) : RegisterEvent()
class RegisterFailed(val msg: String) : RegisterEvent()
class RegisterSuccess(val msg: String) : RegisterEvent()
class Default(val msg: String) : RegisterEvent()

data class RegisterResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String
)

data class RegisterRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)
