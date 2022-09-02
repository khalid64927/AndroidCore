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

package com.khalid.hamid.githubrepos.kaspresso

import com.khalid.hamid.githubrepos.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class LoginScreen : Screen<LoginScreen>() {
    val loginButton = KButton { withId(R.id.btn_login) }
    val registerButton = KButton { withId(R.id.btn_register) }
}

class RegisterScreen : Screen<RegisterScreen>() {
    val usernameEditText = KEditText { withId(R.id.et_username) }
    val pwdEditText = KEditText { withId(R.id.et_pwd) }
    val confirmPwdEditText = KEditText { withId(R.id.et_con_pwd) }
    val registerButton = KButton { withId(R.id.btn_register) }
}

class BalanceScreen : Screen<BalanceScreen>()

class TransferScreen : Screen<TransferScreen>()
