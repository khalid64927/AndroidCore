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

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.databinding.FragmentLoginBinding
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.utilities.autoCleared
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
