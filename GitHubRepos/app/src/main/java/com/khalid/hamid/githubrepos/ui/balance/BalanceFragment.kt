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

package com.khalid.hamid.githubrepos.ui.balance

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentBalanceBinding
import com.khalid.hamid.githubrepos.databinding.ViewTransactionBinding
import com.khalid.hamid.githubrepos.databinding.ViewTransactionBubbleBinding
import com.khalid.hamid.githubrepos.utilities.Prefs
import com.khalid.hamid.githubrepos.utilities.fragmentViewLifecycleDelegate
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject

@AndroidEntryPoint
class BalanceFragment : BaseFragment(), BalanceActionListener {

    @Inject
    lateinit var app: Application

    override val mLayout = R.layout.fragment_balance
    override fun getPageName() = "BalanceFragment"

    @Inject
    lateinit var perf: Prefs
    private var v by fragmentViewLifecycleDelegate({
        binding as FragmentBalanceBinding
    })

    private val balanceViewModel by fragmentViewLifecycleDelegate<BalanceViewModel> ({
        getViewModel()
    })

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.arrange_list, menu)
        menu.findItem(R.id.menu_logout).run {
            setOnMenuItemClickListener {
                perf.clearAllValues()
                val action = BalanceFragmentDirections.actionBalanceFragmentToLoginFragment()
                findNavController().navigate(action)
                false
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.action = this
        v.lifecycleOwner = viewLifecycleOwner
        balanceViewModel.balanceEventLiveData.observe(viewLifecycleOwner) { balanceEvent ->
            when (balanceEvent) {
                is BalanceAvailable -> {
                    v.tvBal.text = "SGD ${currencyFormat(balanceEvent.balance.balance)}"
                    v.tvAccountNo.text = balanceEvent.balance.accountNo
                    v.tvHolder.text = perf.userName
                }
                is TransactionAvailable -> {
                    buildList(balanceEvent.transactionList)
                }
            }
        }
    }

    @SuppressLint("InflateParams")
    private fun buildList(transactionList: Map<String, List<Data>>) {
        for (date in transactionList) {
            var bubble = ViewTransactionBubbleBinding.inflate(layoutInflater)
            date.value.forEach {
                val transaction = ViewTransactionBinding.inflate(layoutInflater)
                transaction.tvTranName.text = it.receipient.accountHolder
                transaction.tvTranAccountNo.text = it.receipient.accountNo
                transaction.tvTranAmount.text = currencyFormat(it.amount.toString())
                if (it.amount < 0) {
                    transaction.tvTranAmount.setTextColor(Color.parseColor("#ffff0000"))
                } else {
                    transaction.tvTranAmount.setTextColor(Color.parseColor("#ff0f9d58"))
                }
                bubble.llBubble.addView(transaction.root)
            }
            v.llBalanceRoot.addView(bubble.llBubble)
        }
    }

    private fun currencyFormat(amount: String): String? {
        val formatter = DecimalFormat("###,###,##0.00")
        return formatter.format(amount.toDouble())
    }

    override fun makeTransfer() {
        val action = BalanceFragmentDirections.actionBalanceFragmentToTransferFragment()
        findNavController().navigate(action)
    }
}

interface BalanceActionListener {
    fun makeTransfer()
}
