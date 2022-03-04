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

package com.khalid.hamid.githubrepos.ui.register

import android.app.Application
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentRegisterBinding
import com.khalid.hamid.githubrepos.utilities.fragmentViewLifecycleDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment(), RegisterActionListener {

    @Inject
    lateinit var app: Application

    override val mLayout = R.layout.fragment_register

    private var v by fragmentViewLifecycleDelegate(
        {binding as FragmentRegisterBinding}
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
