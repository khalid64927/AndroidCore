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

package com.khalid.hamid.githubrepos.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.network.BaseRepository
import com.khalid.hamid.githubrepos.network.onError
import com.khalid.hamid.githubrepos.network.onSuccess
import com.khalid.hamid.githubrepos.testing.OpenForTesting
import com.khalid.hamid.githubrepos.core.BaseViewModel
import com.khalid.hamid.githubrepos.utilities.Prefs
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.inject.Inject
import timber.log.Timber

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
                    //get balance
                perf.accessToken = it.token
                Timber.d("token ${perf.accessToken}")
                perf.accountNumber = it.accountNo
                perf.userName = it.username
                _registerEventLiveData.value = LoginSuccess("")
            }
            Timber.d("five");
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
