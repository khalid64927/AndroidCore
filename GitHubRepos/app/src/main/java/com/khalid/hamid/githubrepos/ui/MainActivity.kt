/*
 * Copyright 2021 Mohammed Khalid Hamid.
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

package com.khalid.hamid.githubrepos.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.core.BaseActivity
import com.khalid.hamid.githubrepos.databinding.ActivityMainBinding
import com.khalid.hamid.githubrepos.utilities.extentions.gone
import com.khalid.hamid.githubrepos.utilities.extentions.visible
import com.khalid.hamid.githubrepos.utilities.toolbar.ToolbarActions
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity(), ToolbarActions {

    // Common loader dialog for all activities
    private var loaderDialog: Dialog? = null

    override val layout = R.layout.activity_main

    private lateinit var binding: ActivityMainBinding

    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this
        setupToolbar()
    }

    override fun setupToolbar() {
        mToolbar = binding.singlePageToolbar
        setSupportActionBar(mToolbar)
        mToolbar.visibility = View.VISIBLE
    }

    /**
     * Common methods to show and hide dialogs from child activities.
     */
    private fun createLoadingDialog(): Dialog {
        val dialog = Dialog(this)

        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(R.layout.layout_loader)
            window?.let {
                it.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                it.setDimAmount(0.5F)
            }
        }
        return dialog
    }

    fun showAlert(context: Context, alertTitle: String, alertMessage: String, cancelable: Boolean) {
        showAlert(
            context,
            alertTitle,
            alertMessage,
            {},
            context?.resources?.getString(R.string.ok),
            cancelable
        )
    }
    open fun showAlert(
        context: Context,
        alertTitle: String,
        alertMessage: String?,
        positiveClickAction: () -> Unit?,
        OkBtnText: String?,
        cancelable: Boolean
    ) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.alert_dialog)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val tvAlertTitle = dialog.findViewById<TextView>(R.id.tv_alert_title)
        tvAlertTitle.text = alertTitle
        val tvAlertMessage = dialog.findViewById<TextView>(R.id.tv_message)
        tvAlertMessage.text = alertMessage
        val btnOK = dialog.findViewById<Button>(R.id.btn_OK)
        btnOK.text = OkBtnText
        btnOK.setOnClickListener {
            dialog.dismiss()
            positiveClickAction()
        }
        dialog.setCancelable(cancelable)
        dialog.show()
    }

    override fun showToolbar() {
        binding.singlePageToolbar.visible()
    }

    override fun hideToolbar() {
        binding.singlePageToolbar.gone()
    }

    fun setupBottomNav() {
        // no-op
    }

    override fun showBottomNavigation() {
        // no-op
    }

    override fun hideBottomNavigation() {
        // no-op
    }

    override fun hideToolbar(isVisible: Boolean) {
        if (isVisible) {
            showToolbar()
        } else {
            hideToolbar()
        }
    }

    override fun setIcon(@DrawableRes drawableRes: Int?) {
        if (drawableRes != null) {
            supportActionBar?.setIcon(drawableRes)
            supportActionBar?.title = null
        } else {
            supportActionBar?.setIcon(null)
        }
    }

    override fun setOverflowMenuColor(@ColorInt color: Int) {
        binding.singlePageToolbar.overflowIcon?.run {
            // val icon = DrawableCompat.wrap(this)
            // DrawableCompat.setTint(icon.mutate(), color)
            // binding.singlePageToolbar.overflowIcon = icon
        }
    }

    override fun setTitleTextColor(@ColorInt color: Int) {
        binding.singlePageToolbar.setTitleTextColor(color)
    }

    override fun setBackArrowColor(@ColorInt color: Int) {
        // changing drawable only if back arrow color is white for substitution
        // since exiting default drawable filter/tint manipulation is not working
        if (color != ContextCompat.getColor(this, R.color.white)) {
            return
        }
        var arrowDrawable = ContextCompat.getDrawable(
            this,
            R.drawable.ic_round_arrow_back_24px
        )
        arrowDrawable?.run {
            val icon = DrawableCompat.wrap(this)
            DrawableCompat.setTint(icon.mutate(), color)
            arrowDrawable = icon
            // binding.singlePageToolbar.navigationIcon = arrowDrawable
        }
    }

    override fun setBackgroundColor(color: Int) {
        binding.singlePageToolbar.setBackgroundColor(color)
    }

    override fun setHomeIconVisibility(isVisible: Boolean) {
        if (!isVisible) {}
        // binding.singlePageToolbar.navigationIcon = null
    }
}
