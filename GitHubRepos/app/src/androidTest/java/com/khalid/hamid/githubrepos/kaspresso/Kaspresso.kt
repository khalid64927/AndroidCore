package com.khalid.hamid.githubrepos.kaspresso

import com.khalid.hamid.githubrepos.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.screen.Screen
import io.github.kakaocup.kakao.text.KButton

class LoginScreen: Screen<LoginScreen>() {
    val loginButton = KButton {withId(R.id.btn_login)}
    val registerButton = KButton {withId(R.id.btn_register)}
}

class RegisterScreen: Screen<RegisterScreen>() {
    val usernameEditText = KEditText { withId(R.id.et_username)}
    val pwdEditText = KEditText { withId(R.id.et_pwd)}
    val confirmPwdEditText = KEditText { withId(R.id.et_con_pwd)}
    val registerButton = KButton {withId(R.id.btn_register)}
}

class BalanceScreen: Screen<BalanceScreen>() {

}

class TransferScreen: Screen<TransferScreen>() {

}

