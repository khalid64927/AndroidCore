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
import timber.log.Timber

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
