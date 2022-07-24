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

package com.khalid.hamid.githubrepos.utilities

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import com.khalid.hamid.githubrepos.R

interface ViewUtils {

    fun hideActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.hide()
    }

    fun showActionBar(supportActionBar: ActionBar?, title: String = "") {
        title?.let { supportActionBar?.title = it }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.show()
    }

    fun dimBehind(popupWindow: PopupWindow) {
        val container = popupWindow.contentView.rootView
        val context = popupWindow.contentView.context
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = p.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.5f
        wm.updateViewLayout(container, p)
    }

    fun createProgressDialog(context: Context?, dialogMessage: String?): ProgressDialog {
        val dialog = ProgressDialog(context)
        dialog.setMessage(dialogMessage)
        dialog.isIndeterminate = true
        dialog.setCancelable(false)
        return dialog
    }

    fun hideKeyboard(context: Context?) {
        if (context == null) return
        try {
            val activity = context as Activity
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun showError(context: Context, e: java.lang.Exception) {
        showAlert(context, e.message.orEmpty())
    }

    fun showError(context: Context, message: String) {
        val alertTitle: String = context.resources.getString(R.string.error)
    }

    fun showAlert(context: Context, alertMessage: String) {
        showAlert(context, alertMessage, true)
    }

    fun showAlert(context: Context, alertMessage: String, cancelable: Boolean) {
        val alertTitle: String = context?.resources?.getString(R.string.alert)
            .orEmpty()
    }

    fun showSingleSelectionDialog(
        context: Context,
        title: String,
        itemSelection: Array<String>,
        onItemSelected: (Int) -> Unit
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setItems(itemSelection) { dialog, indexSelected ->
            onItemSelected.invoke(indexSelected)
            dialog.dismiss()
        }
        return builder.create()
            .apply { show() }
    }

    fun showShortToast(context: Context?, message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

    fun CardView.setCardElevation(@DimenRes elevationRes: Int) {
        context.resources.run {
            val dimension = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                getDimension(elevationRes),
                context.resources.displayMetrics
            )
            cardElevation = dimension
        }
    }

    // Create dialog and assign to the variable if not exist, else show and return the same instance
    fun <T : Dialog?> T?.showSingletonDialog(createDialog: () -> T): T {
        return if (this == null) {
            createDialog.invoke()
                .also {
                    it?.show()
                }
        } else {
            this.show()
            this
        }
    }
}
