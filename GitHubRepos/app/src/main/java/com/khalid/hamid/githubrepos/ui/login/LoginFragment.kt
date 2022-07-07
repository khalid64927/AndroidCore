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

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentLoginBinding
import com.khalid.hamid.githubrepos.utilities.fragmentViewLifecycleDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment(), LoginActionListener {

    @Inject
    lateinit var app: Application

    override val mLayout = R.layout.fragment_login

    private val loginViewModel: LoginViewModel by fragmentViewLifecycleDelegate({
        getViewModel()
    })

    private val v by fragmentViewLifecycleDelegate({
        binding as FragmentLoginBinding
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        v.action = this
        loginViewModel.registerEventLiveData.observe(viewLifecycleOwner) { registerEvent ->
            when (registerEvent) {
                is InvalidPwd -> {
                    v.tvPwdError.text = getString(R.string.msg_incorrect_password)
                    v.tvPwdError.visibility = View.VISIBLE
                }
                is InvalidUserName -> {
                    v.tvUsrError.text = getString(R.string.msg_incorrect_password)
                    v.tvUsrError.visibility = View.VISIBLE
                }
                is LoginSuccess -> {
                    clear()
                    val action = LoginFragmentDirections.actionLoginFragmentToBalanceFragment()
                    findNavController().navigate(action)
                }
                is LoginFailed -> {}
                is Default -> {
                    clear()
                }
            }
        }
    }

    private fun clear() {
        v.etUsername.text.clear()
        v.etPwd.text.clear()
        v.tvUsrError.visibility = View.GONE
        v.tvPwdError.visibility = View.GONE
    }

    override fun login() {
        loginViewModel.login(
            v.etUsername.text.toString(),
            v.etPwd.text.toString()
        )
    }

    override fun register() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
}

interface LoginActionListener {
    fun login()
    fun register()
}
