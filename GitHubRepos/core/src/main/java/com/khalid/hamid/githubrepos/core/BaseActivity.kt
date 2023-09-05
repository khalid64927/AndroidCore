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

package com.khalid.hamid.githubrepos.core

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.khalid.hamid.githubrepos.core.R
import com.khalid.hamid.githubrepos.utilities.ViewUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope, ViewUtils {

    abstract val layout: Int

    // Bundle from intent
    lateinit var bundle: Bundle

    val mContext: Context
        get() = this

    // Override the Coroutine Scope members
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
    private lateinit var job: Job

    // Common loader dialog for all activities
    private var loaderDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layout == -1) {
            // We are not responsible for any UI. Sub class has set it own ContentView
            return
        }

        // Read bundle from incoming bundle
        bundle = intent.extras ?: Bundle()
    }

    /**
     * Override this method in child activities to customize the back pressed events.
     */
    open fun handleBackPress(): Boolean = false

    /**
     * Common methods to show and hide dialogs from child activities.
     */
    private fun createLoadingDialog(): Dialog {
        val dialog = Dialog(mContext)

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

    fun showLoadDialog() {
        if (loaderDialog == null) {
            loaderDialog = createLoadingDialog().apply {
                show()
            }
        } else {
            loaderDialog?.show()
        }
    }

    fun hideLoadDialog() {
        loaderDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }

    /**
     * Override this method in child activities to show Bottom Nav bar.
     */
    open fun showBottomNavigation() { }

    /**
     * Override this method in child activities to hide Bottom Nav bar.
     */
    open fun hideBottomNavigation() { }

    /**
     * Override this method in child activities to setup/show Toolbar.
     */
    open fun setupToolbar() { }

    /**
     * Override this method in child activities to show Toolbar.
     */
    open fun showToolbar() { }

    /**
     * Override this method in child activities to hide Toolbar.
     */
    open fun hideToolbar() { }

    /**
     * Override this method in child activities to clear app data.
     * Useful for clearing when User signs out.
     */
    open fun clearAppData() { }

    /**
     * Override this method in child activities to reset the graph
     * Useful for resetting graph by calling deeplinks.
     * Need this only until we move out the homepage out of picker module
     */
    open fun resetGraph() { }

    /**
     * Override this method in child activities to switch role.
     * Useful for communicating between modules.
     */
    open fun switchRole(roleName: String) { }
}
