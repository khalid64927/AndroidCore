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

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentRegisterBinding
import com.khalid.hamid.githubrepos.utilities.delegates.fragmentViewLifecycleDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment(), RegisterActionListener {

    @Inject
    lateinit var app: Application

    override val mLayout = R.layout.fragment_register

    private var v by fragmentViewLifecycleDelegate(
        { binding as FragmentRegisterBinding }
    )
    private val registerViewModel: RegisterViewModel by fragmentViewLifecycleDelegate({
        getViewModel()
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.action = this
        registerViewModel.registerEventLiveData.observe(viewLifecycleOwner) { registerEvent ->
            var showError = true
            var errorMsg = ""
            when (registerEvent) {
                is IncorrectConfirmPwd -> {
                    errorMsg = getString(R.string.msg_incorrect_confirm_password)
                }
                is InvalidPwd -> {
                    errorMsg = getString(R.string.msg_incorrect_password)
                }
                is InvalidConfirmPwd -> {
                    errorMsg = getString(R.string.msg_incorrect_confirm_password)
                }
                is InvalidUserName -> {
                    errorMsg = getString(R.string.msg_incorrect_username)
                }
                is RegisterFailed -> {
                    showError = true
                }
                is Registered -> {
                    showError = false
                    v.tvError.visibility = View.GONE
                    clear()
                    val action = RegisterFragmentDirections.actionRegisterFragmentToBalanceFragment()
                    findNavController().navigate(action)
                }
                is MismatchPwd -> {
                    errorMsg = getString(R.string.msg_password_mismatch)
                }
                is Default -> {
                    showError = false
                }
                else -> {
                    // no-op
                }
            }
            v.tvError.visibility = if (showError) View.VISIBLE else View.GONE
            if (showError) v.tvError.text = errorMsg
        }
    }

    private fun clear() {
        v.etUsername.text.clear()
        v.etPwd.text.clear()
        v.etConPwd.text.clear()
    }

    override fun register() {
        registerViewModel.register(
            v.etUsername.text.toString(),
            v.etPwd.text.toString(),
            v.etConPwd.text.toString()
        )
    }
}

interface RegisterActionListener {
    fun register()
}
