package com.khalid.hamid.githubrepos.utilities

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes

/**
 * Should be implemented by activity or view that contains toolbar
 * and can configure its view properties
 * To be used by fragment to propagate toolbar configs
 */
interface ToolbarActions {
    fun hideToolbar(isVisible: Boolean)
    fun setIcon(@DrawableRes drawableRes: Int?)
    fun setOverflowMenuColor(@ColorInt color: Int)
    fun setTitleTextColor(@ColorInt color: Int)
    fun setBackArrowColor(@ColorInt color: Int)
    fun setBackgroundColor(@ColorInt color: Int)
    fun setHomeIconVisibility(isVisible: Boolean)
}
