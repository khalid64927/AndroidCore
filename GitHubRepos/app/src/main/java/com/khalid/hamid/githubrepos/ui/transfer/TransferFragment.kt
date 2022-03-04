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

package com.khalid.hamid.githubrepos.ui.transfer

import android.app.Application
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.annotations.SerializedName
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseFragment
import com.khalid.hamid.githubrepos.databinding.FragmentTransferBinding
import com.khalid.hamid.githubrepos.utilities.autoCleared
import com.khalid.hamid.githubrepos.utilities.fragmentViewLifecycleDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransferFragment : BaseFragment(), TransferActionListener {

    @Inject
    lateinit var app: Application
    override val mLayout = R.layout.fragment_transfer
    private var v by fragmentViewLifecycleDelegate(
        {binding as FragmentTransferBinding }
    )
    var selectedPayee = ""

    private val transferViewModel: TransferViewModel by fragmentViewLifecycleDelegate({
        getViewModel()
    })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v.action = this
        transferViewModel.transferEventLiveData.observe(viewLifecycleOwner) { transferEvent ->
            var showError = true
            var errorMsg = ""
            when (transferEvent) {
                is TransferSuccess -> {
                    showError = false
                    clear()
                    findNavController().navigateUp()
                }
                is TransferFailed -> {
                    errorMsg = "Failed to transfer"
                }
                is GetPayeesSuccess -> {
                    showError = false
                    val payees = transferEvent.payeeResponse
                    setSpinner(payees)
                }
                is GetPayeesFailed -> {
                    errorMsg = "Failed to get payee list"
                }
                is InvalidAmount -> {
                    errorMsg = "Invalid amount"
                }
                is InvalidDescription -> {
                    errorMsg = "Incorrect description text"
                }
                is InvalidPayee -> {
                    errorMsg = "Incorrect Payee"
                }
            }
            v.tvTransferError.visibility = if (showError) View.VISIBLE else View.GONE
            if (showError) v.tvTransferError.text = errorMsg
        }
    }




    private fun setSpinner(payees: PayeeResponse) {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val payees = payees.data.map { it.name }.toMutableList()
        activity?.let {
            val adapter = ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                payees
            )
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            v.payeeSpinner.adapter = adapter
            v.payeeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    Toast.makeText(it, "${payees[position]}", Toast.LENGTH_SHORT).show()
                    selectedPayee = payees[position] }
                }
            }
        }

    private fun clear() {
        v.etTransferAmount.text.clear()
        v.etTransferDescription.text.clear()
    }

    override fun transfer() {
        transferViewModel.transfer(
            selectedPayee,
            v.etTransferAmount.text.toString().trim(),
            v.etTransferDescription.text.toString().trim())
    }
}

interface TransferActionListener {
    fun transfer()
}

data class PayeeResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("status")
    val status: String
)

data class Data(
    @SerializedName("accountNo")
    val accountNo: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)
