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
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.khalid.hamid.githubrepos.R
import com.khalid.hamid.githubrepos.utilities.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
abstract class BaseFragment :
    Fragment(),
    CoroutineScope,
    ViewUtils,
    GetViewModelProvider,
    FragmentViewCallbackProvider {

    @get:LayoutRes
    abstract val mLayout: Int

    @Inject
    lateinit var pickerViewModelProvider: AppViewModelProvider

    abstract fun getPageName(): String?

    lateinit var mContext: Context
    var appCompatActivity: AppCompatActivity? = null

    private lateinit var job: Job

    override var onViewCreatedListeners = mutableListOf<OnViewCreatedListener>()

    // Override the CoroutineScope members
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    open var binding: ViewDataBinding? = null

    override fun getViewModelFactory() = pickerViewModelProvider

    override fun <T : ViewModel> viewModelCreated(viewModel: T) {
    }

    private val clearCacheConfirmationDialog: Dialog by autoClearDialog({
        // show dialog
        Dialog(this.mContext)
    })

    /**
     * Provides a default toolbar.
     * Overwrite in child fragment to provide own [ToolbarConfig]
     */
    open val toolbarConfig: ToolbarConfig =
        DefaultToolbar()

    private val mToolbarMenuList = mutableListOf<ToolbarMenuConfig>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbarConfig.run {
            if (showOverFlow) {
                setHasOptionsMenu(true)
                mToolbarMenuList.clear()

                if (toolbarMenuConfig.filter {
                    it.menuRes == R.menu.default_toolbar_menu
                }
                    .isNullOrEmpty()
                ) {
                    // Default menu for all overflow menu
                    mToolbarMenuList.add(
                        getBaseToolbarMenuOption {
                            clearCacheConfirmationDialog.show()
                        }
                    )
                }
                mToolbarMenuList.addAll(toolbarMenuConfig)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, mLayout, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner

        setToolbarConfig(toolbarConfig)

        return binding?.root ?: inflater.inflate(mLayout, container, false)
    }

    /**
     * Child fragment can overwrite this to true to block back press (do nothing on back press)
     */
    open val overwriteBackPress: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedListeners.forEach {
            it.onViewCreateCalled()
        }
        /**
         * Block back press only when child fragment configure overwriteBackPress to true
         */
        if (overwriteBackPress) {
            setBackPress {
                // no-op
            }
        }
    }

    override fun addOnViewCreatedListener(onViewCreatedListener: OnViewCreatedListener) {
        onViewCreatedListeners.add(onViewCreatedListener)
    }

    inline fun <reified actualBinding : ViewDataBinding> getFragmentBinding() =
        binding as actualBinding

    /**
     * Add additional menu item
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        mToolbarMenuList.forEach { toolbarData ->
            inflater.inflate(toolbarData.menuRes, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val toolbarMenuActions = mToolbarMenuList.flatMap {
            it.menuActionList
        }
        toolbarMenuActions.forEach {
            if (item.itemId == it.menuItemRes) {
                it.menuAction.invoke()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Call this method to customize the back pressed events.
     * Note: This will overwrite the default navigate up behaviour. It is up to the
     * child class to decide the behaviour of back press in [setBackPress]
     *
     * Multiple calls will override previous back press action
     */
    protected fun setBackPress(onBackPress: () -> Unit) {
        val backPressCallBack = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                onBackPress.invoke()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backPressCallBack
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        logBreadcrumb("onActivityCreated" + getPageName())
    }

    protected fun logBreadcrumb(crumb: String) {
        // TODO: log
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = requireContext()
        appCompatActivity = requireActivity() as AppCompatActivity
        // Attaching the instance of the Job to the instance of the Activity
        job = Job()
    }

    override fun onPause() {
        job.cancel()
        super.onPause()
    }

    protected open fun dismissDialog(progressDialog: ProgressDialog?) {
        progressDialog?.dismiss()
    }

    override fun onDestroyView() {
        hideLoader()
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        onViewCreatedListeners.clear()
        super.onDestroy()
    }

    override fun removeOnViewCreatedListener(onViewCreatedListener: OnViewCreatedListener) {
        onViewCreatedListeners.remove(onViewCreatedListener)
    }

    fun showLoader() = (requireActivity() as BaseActivity).showLoadDialog()
    fun hideLoader() = (requireActivity() as BaseActivity).hideLoadDialog()

    open fun onPageLoaded(message: String = "") {}

    inline fun <reified VM : ViewModel> Fragment.getViewModel(): VM {
        val vm = pickerViewModelProvider.getViewModel(VM::class, this)
        if (vm is BaseViewModel) {
            vm.errorEvent.observeErrorEvent(viewLifecycleOwner) {
                it.message?.run {
                    if (isNotBlank()) {
                        showAlert(mContext, this)
                    }
                }
            }
            vm.navigateToLiveEvent.observeEvent(viewLifecycleOwner) { navDir ->
                hideLoader()
                when (navDir) {
                    is CustomNavDirection.NativeNavDirection -> {
                        if (navDir.result != null) {
                            navigateWithResult(
                                navDir.navDirections, navDir.result.first, navDir.result.second
                            )
                        } else {
                            findNavController().safeNavigate(
                                navDir.navDirections
                            )
                        }
                    }
                    is CustomNavDirection.UriNavDirection -> findNavController().safeNavigate(
                        navDir.uri
                    )
                    is CustomNavDirection.NavigateUpDirection -> findNavController().navigateUp()
                    CustomNavDirection.RootHomeDirection -> (appCompatActivity as NavigationHost).popToHome()
                }
            }
            vm.asyncState.observe(
                viewLifecycleOwner
            ) {
                it?.let { asyncState ->
                    when (asyncState.status) {
                        State.SUCCESS -> {
                            hideLoader()
                        }
                        State.FAILED -> {
                            hideLoader()
                        }
                        State.RUNNING -> {
                            showLoader()
                        }
                        else -> {
                            hideLoader()
                        }
                    }
                }
            }
            vm.clearDataEvent.observeEvent(viewLifecycleOwner) { isCleared ->
                if (isCleared) {
                    (appCompatActivity as NavigationHost).resetGraph()
                } else {
                    showError(mContext, getString(R.string.reset_data_error))
                }
            }
        }
        return vm
    }

    protected fun setToolbarTitle(titleText: String) {
        appCompatActivity?.supportActionBar?.title = titleText
    }

    private fun setToolbarConfig(toolbarConfig: ToolbarConfig) {
        (appCompatActivity as? ToolbarActions)?.run {
            hideToolbar(toolbarConfig.showToolbar)
            setIcon(toolbarConfig.iconDrawable)
            setOverflowMenuColor(ContextCompat.getColor(mContext, toolbarConfig.overflowColor))
            setTitleTextColor(ContextCompat.getColor(mContext, toolbarConfig.titleTextColor))
            setBackArrowColor(ContextCompat.getColor(mContext, toolbarConfig.backArrowColor))
            setBackgroundColor(ContextCompat.getColor(mContext, toolbarConfig.bgColor))
            setHomeIconVisibility(toolbarConfig.showHomeIcon)
        }
    }

    /**
     * Navigate up with result. To get result, use [getNavigationResultAndClear]
     */
    fun navigateUpWithResult(resultKey: String, result: Any) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(resultKey, result)
        findNavController().navigateUp()
    }

    /**
     * Navigate to any direction with result. To get result, use [getNavigationResultAndClear]
     * This allow one-shot events instead of relying on navargs which will always be present
     *
     * It also supports more object types than nav args
     */
    fun navigateWithResult(navDirections: NavDirections, resultKey: String, result: Any) {
        val destId = getNavDestinationIdFromNavDirection(navDirections) ?: return

        findNavController().getBackStackEntry(destId)
            .run {
                savedStateHandle.set(resultKey, result)
            }
        findNavController().safeNavigate(navDirections)
    }

    /**
     * Get the nav destination id from nav direction object
     */
    private fun getNavDestinationIdFromNavDirection(navDirections: NavDirections): Int? {
        var destId = 0
        findNavController().run {
            // If action can be found from current destination in nav graph
            destId = currentDestination?.getAction(navDirections.actionId)?.destinationId.orZero()

            if (destId == 0) {
                // If action can be found from global nav graph action
                destId = graph.getAction(navDirections.actionId)?.destinationId.orZero()
            }

            if (destId == 0) {
                // If action is not a new destination but a pop up to
                destId = currentDestination?.getAction(
                    navDirections.actionId
                )?.navOptions?.popUpToId.orZero()
            }
        }
        return if (destId == 0) null else destId
    }

    /**
     * Gets result key's live data from shared view model in saved state and clears it.
     * Subsequent calls to get result will be null
     */
    protected inline fun <reified T> getNavigationResultAndClear(resultKey: String): T? =
        findNavController().currentBackStackEntry?.run {
            val result = savedStateHandle.getLiveData<T>(
                resultKey
            ).value
            savedStateHandle.remove<T>(resultKey)
            return result
        }
}
